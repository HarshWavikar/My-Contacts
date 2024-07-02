package com.codewithharsh.mycontacts.feature_contact.domain.util

sealed class ContactOrder(
    val orderType: OrderType
) {
    class OrderByFirstName(orderType: OrderType): ContactOrder(orderType)
    class OrderByLastName(orderType: OrderType): ContactOrder(orderType)

    fun copy(orderType: OrderType): ContactOrder {
        return when(this){
            is OrderByFirstName -> OrderByFirstName(orderType)
            is OrderByLastName -> OrderByFirstName(orderType)
        }
    }
}