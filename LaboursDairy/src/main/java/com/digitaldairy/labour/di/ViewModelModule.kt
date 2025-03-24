package com.digitaldairy.labour.di

import com.digitaldairy.labour.listing.IPersonListingViewModel
import com.digitaldairy.labour.listing.PersonListingViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindPersonListingViewModel(
        impl: PersonListingViewModel
    ): IPersonListingViewModel
}