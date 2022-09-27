package com.sergiom.beersearcher.injector

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sergiom.data.local.AppDatabase
import com.sergiom.data.local.BeerItemsDao
import com.sergiom.data.mappers.*
import com.sergiom.data.models.*
import com.sergiom.data.net.Api
import com.sergiom.data.net.RestClient
import com.sergiom.data.net.RestClientImpl
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.net.response.BeerEntity
import com.sergiom.data.net.response.IngredientEntity
import com.sergiom.data.net.response.IngredientsEntity
import com.sergiom.data.repository.BeerRepository
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.repositoryimpl.NetRepositoryImpl
import com.sergiom.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /*
    * NETWORK
    * */
    @Singleton
    @Provides
    fun provideRemoteCaller(gson: Gson): Api = RestClientImpl().getRemoteCaller()

    @Provides
    fun provideRestClient(): RestClient = RestClientImpl()

    @Provides
    fun provideNetRepository(restClient: RestClient): NetRepository = NetRepositoryImpl(restClient)

    /*
    * DATA BASE
    * */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideEventsDao(db: AppDatabase) = db.eventsDao()

    @Singleton
    @Provides
    fun provideBeerRepository(localDataSource: BeerItemsDao, mapper: BeerDataBaseMapper) = BeerRepository(localDataSource, mapper)

    /*
    * MAPPERS
    * */
    @Provides
    fun bindsBeerDataMapper(beerMapper: BeerMapper): Mapper<BeerDataEntity, BeerDataModel> = BeerDataMapper(beerMapper)

    @Provides
    fun bindsBeerMapper(ingredientsMapper: IngredientsMapper): Mapper<BeerEntity, BeerModel> = BeerMapper(ingredientsMapper)

    @Provides
    fun bindsIngredientsMapper(ingredient: IngredientMapper): Mapper<IngredientsEntity?, IngredientsModel> = IngredientsMapper(ingredient)

    @Provides
    fun bindsIngredientMapper(): Mapper<IngredientEntity, IngredientModel> = IngredientMapper()

    @Provides
    fun bindsBeerDataBaseMapper(ingredientsDBMapper: IngredientsDataBaseMapper): Mapper<BeerModel, BeerItemDataBase> = BeerDataBaseMapper(ingredientsDBMapper)

    @Provides
    fun bindsIngredientsDataBaseMapper(ingredientDBMapper: IngredientDataBaseMapper): Mapper<IngredientsModel, IngredientsDB> = IngredientsDataBaseMapper(ingredientDBMapper)

    @Provides
    fun bindsIngredientDataBaseMapper(): Mapper<IngredientModel, IngredientDB> = IngredientDataBaseMapper()

    @Provides
    fun bindsBeerDBToDataMapper(ingredientsMapper: IngredientsDBToDataMapper): Mapper<BeerItemDataBase, BeerModel> = BeerDBToBeerDataMapper(ingredientsMapper)

    @Provides
    fun bindsIngredientsDBToDataMapper(ingredient: IngredientDBToDataMapper): Mapper<IngredientsDB, IngredientsModel> = IngredientsDBToDataMapper(ingredient)

    @Provides
    fun bindsIngredientDBToDataMapper(): Mapper<IngredientDB, IngredientModel> = IngredientDBToDataMapper()

    /*
    * NETWORK USE CASES
    * */
    @Provides
    fun providesGetBeersUseCase(netRepository: NetRepository, mapper: BeerDataMapper): GetBeersUseCase = GetBeersUseCaseImpl(netRepository, mapper)

    @Provides
    fun providesGetBeerSearchUseCase(netRepository: NetRepository, mapper: BeerDataMapper): GetBeerSearchUseCase = GetBeerSearchUseCaseImpl(netRepository, mapper)

    /*
    * DATA BASE USE CASES
    * */
    @Provides
    fun providesDeleteBeersFromDataBaseUseCase(beerRepository: BeerRepository): DeleteBeersFromDataBaseUseCase = DeleteBeersFromDataBaseUseCaseImpl(beerRepository)

    @Provides
    fun providesDeleteOneBeerFromDataBaseUseCase(beerRepository: BeerRepository): DeleteOneBeerFromDataBaseUseCase = DeleteOneBeerFromDataBaseUseCaseImpl(beerRepository)

    @Provides
    fun providesGetBeerByIdFromDataBaseUseCase(beerRepository: BeerRepository): GetBeerByIdFromDataBaseUseCase = GetBeerByIdFromDataBaseUseCaseImpl(beerRepository)

    @Provides
    fun providesGetBeersFromDataBaseUseCase(beerRepository: BeerRepository): GetBeersFromDataBaseUseCase = GetBeersFromDataBaseUseCaseImpl(beerRepository)

    @Provides
    fun providesSaveBeerToDataBaseUseCase(beerRepository: BeerRepository): SaveBeerToDataBaseUseCase = SaveBeerToDataBaseUseCaseImpl(beerRepository)
}