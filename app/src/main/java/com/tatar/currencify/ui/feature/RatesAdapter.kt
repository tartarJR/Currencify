package com.tatar.currencify.ui.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tatar.currencify.dagger.rates.scope.ActivityScope
import com.tatar.currencify.databinding.LayoutRatesRowBinding
import com.tatar.currencify.domain.feature.RateEntity
import com.tatar.currencify.ui.feature.RatesDiffUtil.Companion.KEY_CURRENCY_CODE
import com.tatar.currencify.ui.feature.RatesDiffUtil.Companion.KEY_RATE
import com.tatar.currencify.ui.util.toCurrencyFlag
import com.tatar.currencify.ui.util.toCurrencyString
import javax.inject.Inject

@ActivityScope
class RatesAdapter @Inject constructor() :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    var rates: List<RateEntity> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(
                RatesDiffUtil(rates, value)
            ).dispatchUpdatesTo(this)

            field = value
        }

    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutRatesRowBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)

        setItemClickListener(viewHolder)

        return viewHolder
    }

    private fun setItemClickListener(viewHolder: RatesAdapter.ViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position == -1) return@setOnClickListener

            itemClickListener.onItemClick(rates[position].currencyCode)
        }
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            with(payloads.first()) {
                if (this is Bundle) {
                    val currencyCode = getString(KEY_CURRENCY_CODE)
                    val rate = getDouble(KEY_RATE)

                    with(holder.binding) {
                        if (currencyCode != null) currencyAbbTv.text = currencyCode
                        if (rate != 0.0) currencyAmountEt.setText(String.format("%.3f", rate))
                    }
                }
            }
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(
        val binding: LayoutRatesRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rateEntity: RateEntity) {
            with(binding) {
                currencyIv.setImageResource(rateEntity.currencyCode.toCurrencyFlag())
                currencyAbbTv.text = rateEntity.currencyCode
                currencyNameTv.text = rateEntity.currencyCode.toCurrencyString()
                currencyAmountEt.setText(String.format("%.3f", rateEntity.rate))
            }
        }
    }
}

interface ItemClickListener {
    fun onItemClick(newBaseCurrency: String)
}

class RatesDiffUtil(
    private val oldList: List<RateEntity>,
    private val newList: List<RateEntity>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currencyCode == newList[newItemPosition].currencyCode
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val bundle = Bundle().apply {
            if (oldItem.currencyCode != newItem.currencyCode)
                putString(KEY_CURRENCY_CODE, newItem.currencyCode)
            if (oldItem.rate != newItem.rate)
                putDouble(KEY_RATE, newItem.rate)
        }

        return if (bundle.size() == 0)
            null
        else bundle
    }

    companion object {
        const val KEY_CURRENCY_CODE = "KEY_CURRENCY_CODE"
        const val KEY_RATE = "KEY_RATE"
    }
}
