package com.codertal.studybook.data.modules;

import com.codertal.studybook.data.users.UsersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {

    @Provides
    UsersRepository provideUsersRepository() {
        return new UsersRepository();
    }

}
