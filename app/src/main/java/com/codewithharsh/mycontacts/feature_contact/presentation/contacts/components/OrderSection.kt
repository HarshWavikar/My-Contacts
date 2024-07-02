package com.codewithharsh.mycontacts.feature_contact.presentation.contacts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewithharsh.mycontacts.feature_contact.domain.util.ContactOrder
import com.codewithharsh.mycontacts.feature_contact.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    contactOrders: ContactOrder = ContactOrder.OrderByFirstName(OrderType.Descending),
    onOrderChange: (ContactOrder) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            DefaultRadioButton(
                text = "First Name",
                selected = contactOrders is ContactOrder.OrderByFirstName,
                onSelect = { onOrderChange(ContactOrder.OrderByFirstName(contactOrders.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Last Name",
                selected = contactOrders is ContactOrder.OrderByLastName,
                onSelect = { onOrderChange(ContactOrder.OrderByLastName(contactOrders.orderType)) }
            )
        }
//        Spacer(modifier = Modifier.height(2.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = contactOrders.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(contactOrders.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = contactOrders.orderType is OrderType.Descending,
                onSelect = { onOrderChange(contactOrders.copy(OrderType.Descending)) }
            )
        }
    }
}