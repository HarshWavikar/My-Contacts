package com.codewithharsh.mycontacts.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codewithharsh.mycontacts.feature_contact.data.data_source.ContactDatabase
import com.codewithharsh.mycontacts.feature_contact.data.data_source.ContactDatabase.Companion.DATABASE_NAME
import com.codewithharsh.mycontacts.feature_contact.data.repository.ContactRepositoryImpl
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.ContactUseCase
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.DeleteContactUseCase
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.GetContactsUseCase
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.InsertContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): RoomDatabase {
        return Room.databaseBuilder(
            application,
            ContactDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesContactRepository(contactDatabase: ContactDatabase): ContactRepository {
        return ContactRepositoryImpl(contactDatabase.contactDao())
    }

    @Provides
    @Singleton
    fun providesContactUseCase(contactRepository: ContactRepository): ContactUseCase {
        return ContactUseCase(
            getContactsUseCase = GetContactsUseCase(contactRepository),
            insertContactUseCase = InsertContactUseCase(contactRepository),
            deleteContactUseCase = DeleteContactUseCase(contactRepository)
        )
    }
}