package repos

import models.LoginModel

interface IMongoRepo {
    fun createLogin(login: LoginModel): Boolean
}