package com.example.chat.presentation.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.chat.AbsMessage
import com.example.chat.presentation.model.chat.SimpleUserMessage
import com.example.chat.presentation.ui.adapter.holder.*
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.chat.presentation.model.chat.AbsMessageText


class ChatAdapter(context: Context) : RecyclerView.Adapter<AbsChatViewHolder>() {

    private val cachedViews = mutableListOf<Pair<MessageType, MutableList<View>>>()
    private val asyncListDiffer: AsyncListDiffer<AbsMessage> = AsyncListDiffer(this, DIFF_CALLBACK)

    init {
        buildCachedViewsList(context)
    }

    fun update(items: List<AbsMessage>) {
        asyncListDiffer.submitList(items)
    }

    override fun getItemId(position: Int): Long = asyncListDiffer.currentList[position].id.hashCode().toLong()

    override fun getItemViewType(position: Int): Int = asyncListDiffer.currentList[position].type.hashCode()

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsChatViewHolder {
        val messageType = MessageType.getTypeByHashCode(viewType)
        val view = createViewByMessageType(parent, messageType)
        /**
         * Определяем класс ViewHolder'а
         *
         * Используем абстрактную модель вьюхолдера с минимумом информации (viewType), т.к. айтемы в чате могут быть абсолютно разнообразные,
         * полноэкранные, со вложенными списками и прочим.
         */
        return when (messageType) {
            MessageType.UNKNOWN -> {
                ChatViewHolderEmpty(view)
            }
            MessageType.SIMPLE_TRANSPARENT_DIVIDER -> {
                ChatViewHolderSimpleDivider(view)
            }
            MessageType.INCOMING_TEXT -> {
                // Проделываем операции с вьюхой, назначаем слушателей и отправляем ее во ViewHolder
                view.setOnClickListener {
                    //
                }
                ChatViewHolderIncomingTextSimple(view)
            }
            MessageType.OUTGOING_TEXT -> {
                view.setOnClickListener {
                    //
                }
                ChatViewHolderOutgoingTextSimple(view)
            }
        }
    }

    override fun onBindViewHolder(holder: AbsChatViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolderIncomingTextSimple -> holder.bind(asyncListDiffer.currentList[position] as SimpleUserMessage)
            is ChatViewHolderOutgoingTextSimple -> holder.bind(asyncListDiffer.currentList[position] as SimpleUserMessage)
        }
    }

    /**
     * Здесь мы собираем весь список типов ViewHolder'ов и асинхронно создаем вьюхи
     *
     * Как это работает: при инициализации адаптера сразу начинают создаваться вьюхи всех возможных
     * вьютайпов, которые берутся из Енама [MessageType], поскольку это все работает в главном потоке,
     * то первые вьюхи в списке чатов создаются синхронно, а все остальные прогоняются по циклу и создаются при помощи
     * [AsyncLayoutInflater] асинхронно. Далее, при скролле у нас вызывается [onCreateViewHolder], и уже созданные вьюхи
     * достаются из массива [cachedViews] и отправляются в нужный вьюхолдер. Это обеспечивает плавный скролл даже с большим
     * колличеством вьюхолдеров.
     */
    private fun buildCachedViewsList(context: Context) {
        val asyncLayoutInflater = AsyncLayoutInflater(context)
        val typesList = MessageType.values()
        typesList.forEach { messageType ->
            cachedViews.add(Pair(messageType, mutableListOf()))
            repeat(CACHED_VIEWS_POOL_SIZE) {
                asyncLayoutInflater.inflate(messageType.layoutId, null) { view, _, _ ->
                    val viewsListPair = cachedViews.find { it.first == messageType }
                    viewsListPair?.second?.add(view)
                }
            }
        }
    }

    /**
     * Здесь забираем уже созданную в бэкграунд потоке вьюху если она успела создасться, или инфлейтим новую
     */
    private fun createViewByMessageType(parent: ViewGroup, messageType: MessageType): View {
        val cachedTypeListView = cachedViews.find { it.first == messageType }
        return if (cachedTypeListView != null && cachedTypeListView.second.isNotEmpty()) {
            cachedTypeListView.second.removeLast().also {
                it.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            }
        } else {
            LayoutInflater.from(parent.context).inflate(messageType.layoutId, parent, false)
        }
    }


    companion object {
        // количество вьюх, которые мы создаем асинхронно и помещаем в массив [cachedViews]
        private const val CACHED_VIEWS_POOL_SIZE = 5

        private val DIFF_CALLBACK: DiffUtil.ItemCallback<AbsMessage> = object : DiffUtil.ItemCallback<AbsMessage>() {
            override fun areItemsTheSame(oldItem: AbsMessage, newItem: AbsMessage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AbsMessage, newItem: AbsMessage): Boolean {
                if (oldItem is AbsMessageText && newItem is AbsMessageText) {
                    return oldItem.text == newItem.text
                            && oldItem.time == newItem.time
                }
                return oldItem.time == newItem.time
            }
        }
    }
}