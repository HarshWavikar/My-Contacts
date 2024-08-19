package com.codewithharsh.mycontacts.feature_contact.presentation.contacts.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ContactItem(
    animatedVisibilityScope: AnimatedVisibilityScope,
    contact: Contact,
    onCallClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${contact.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 200, easing = FastOutSlowInEasing)
                        }
                    )
                    .size(70.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                contact.image?.let {
                    Image(
                        bitmap = BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap(),
                        contentDescription = "Contact Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                } ?:
                Text(
                    text = "${contact.firstName.first()}${contact.lastName.first()}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${contact.id}/${contact.firstName}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 800)
                            }
                        )
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${contact.id}/${contact.lastName}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 800)
                            }
                        )
                    ,
                    text = contact.firstName + " " + contact.lastName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${contact.email}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 1000)
                            }
                        )
                        .offset (y = (-8).dp),
                    text = "(${contact.email})",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
                Text(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${contact.phone}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 1200)
                            }
                        ).offset (y = (-5).dp),
                    text = contact.phone,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { onCallClick(contact.phone) },
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


