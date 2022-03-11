package com.hamekara.app.model.remote.domain

import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import com.hamekara.app.App
import com.hamekara.app.R
import com.hamekara.app.model.local.entity.MetaOrderEntityStruct
import com.hamekara.app.model.local.entity.OrderEntityStruct
import com.hodhod.tv.ui.adapter.DiffItem
import java.text.DecimalFormat

data class OrderStruct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("total")
    val total: String = "",

    @SerializedName("created_at")
    val created_at: String = "",

    @SerializedName("updated_at")
    val updated_at: String = "",
    @SerializedName("meta_orders")
    val metaOrders: List<MetaOrder>?
) : DiffItem {
    companion object {
        fun convert(orderEntityStruct: OrderEntityStruct)=
            OrderStruct(
                id = orderEntityStruct.id,
                status = orderEntityStruct.status,
                name = orderEntityStruct.name,
                address = orderEntityStruct.address,
                phone = orderEntityStruct.phone,
                total = orderEntityStruct.total,
                created_at = orderEntityStruct.created_at,
                updated_at = orderEntityStruct.updated_at,
                metaOrders = orderEntityStruct.metaOrders.map {
                    MetaOrder.convert(it)!!
                }
            )
    }

    fun getPriceCommaSeparated(): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(total.toInt()).plus(" تومان")
    }

    fun statusToString():String{
        return when(status){
            "failed" -> "ناموفق"
            "completed" -> "تکمیل سفارش"
            "ready_for_send" -> "در انتظار تایید پذیرنده"
            "payment" -> "در انتظار پرداخت"
            "delivery" -> "در حال ارسال محصول"
            else -> "نا مشخص"
        }
    }

    fun statusColor():Int{
        return when(status){
            "failed" -> ContextCompat.getColor(App.instance, R.color.errorColor)
            "completed" -> ContextCompat.getColor(App.instance, R.color.successColor)
            "pending" -> ContextCompat.getColor(App.instance, R.color.gray)
            else -> ContextCompat.getColor(App.instance, R.color.gray)
        }
    }
}

data class MetaOrder(
    @SerializedName("id")
    val id: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("product")
    val productStruct: ProductStruct?,
):DiffItem {
    companion object {
        fun convert(metaOrderEntityStruct: MetaOrderEntityStruct) =
            metaOrderEntityStruct.productStruct?.let { ProductStruct.convert(it) }?.let {
                MetaOrder(
                    id = metaOrderEntityStruct.id,
                    productStruct = it,
                    count = metaOrderEntityStruct.count
                )
            }
    }
}