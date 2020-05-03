package com.tatar.currencify.ui.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tatar.currencify.dagger.currency.scope.ActivityScope
import com.tatar.currencify.databinding.LayoutConvertedCurrencyRowBinding
import com.tatar.currencify.presentation.feature.ConvertedCurrency
import com.tatar.currencify.ui.feature.ConvertedCurrenciesDiffUtil.Companion.KEY_AMOUNT
import com.tatar.currencify.ui.feature.ConvertedCurrenciesDiffUtil.Companion.KEY_CURRENCY_CODE
import com.tatar.currencify.ui.util.toCurrencyFlag
import com.tatar.currencify.ui.util.toCurrencyString
import javax.inject.Inject

@ActivityScope
class ConvertedCurrenciesAdapter @Inject constructor(
) : RecyclerView.Adapter<ConvertedCurrenciesAdapter.ViewHolder>() {

    var convertedCurrencyList: List<ConvertedCurrency> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(
                ConvertedCurrenciesDiffUtil(convertedCurrencyList, value)
            ).dispatchUpdatesTo(this)

            field = value
        }

    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutConvertedCurrencyRowBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)

        setItemClickListener(viewHolder)

        return viewHolder
    }

    private fun setItemClickListener(viewHolder: ConvertedCurrenciesAdapter.ViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position == -1) return@setOnClickListener

            itemClickListener.onItemClick(convertedCurrencyList[position].currencyCode)
        }
    }

    override fun getItemCount(): Int = convertedCurrencyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(convertedCurrencyList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            with(payloads.first()) {
                if (this is Bundle) {
                    val currencyCode = getString(KEY_CURRENCY_CODE)
                    val amount = getString(KEY_AMOUNT)

                    with(holder.binding) {
                        if (currencyCode != null) currencyAbbTv.text = currencyCode
                        if (amount != null) currencyAmountEt.setText(amount)
                    }
                }
            }
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(
        val binding: LayoutConvertedCurrencyRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(convertedCurrency: ConvertedCurrency) {
            with(binding) {
                currencyIv.setImageResource(convertedCurrency.currencyCode.toCurrencyFlag())
                currencyAbbTv.text = convertedCurrency.currencyCode
                currencyNameTv.text = convertedCurrency.currencyCode.toCurrencyString()
                currencyAmountEt.setText(String.format("%.3f", convertedCurrency.amount))
            }
        }
    }
}

interface ItemClickListener {
    fun onItemClick(baseCurrency: String)
}

class ConvertedCurrenciesDiffUtil(
    private val oldList: List<ConvertedCurrency>,
    private val newList: List<ConvertedCurrency>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currencyCode == newList[newItemPosition].currencyCode
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val bundle = Bundle().apply {
            if (oldItem.currencyCode != newItem.currencyCode)
                putString(KEY_CURRENCY_CODE, newItem.currencyCode)
            if (oldItem.amount != newItem.amount)
                putString(KEY_AMOUNT, String.format("%.3f", newItem.amount))
        }

        return if (bundle.size() == 0)
            null
        else bundle
    }

    companion object {
        const val KEY_CURRENCY_CODE = "KEY_CURRENCY_CODE"
        const val KEY_AMOUNT = "KEY_AMOUNT"
    }
}
