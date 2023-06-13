package com.example

import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@Singleton
class DemoController : DemoServiceGrpcKt.DemoServiceCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest) = HelloReply
        .newBuilder()
        .setMessage("Hello ${request.name}")
        .build()

    override suspend fun sayHelloAgain(request: HelloRequest) = HelloReply
        .newBuilder()
        .setMessage("Hello again ${request.name}")
        .build()

    override suspend fun saveUser(request: SaveUserRequest): UserResponse {
        return UserResponse.newBuilder()
            .setId(1)
            .setName(request.name)
            .setLastName(request.lastName)
            .build()
    }
    override fun saveUserStream(requests: Flow<SaveUserRequest>): Flow<UserResponse> = flow {
        var id = 1
        requests.collect {
            println("Saving user...")
            emit(
                UserResponse.newBuilder()
                    .setId(id++)
                    .setName(it.name)
                    .setLastName(it.lastName)
                    .build()
            )
            println("Finished...")
        }
    }

}