package com.xdlr.temperaturerecord.repo;

import com.xdlr.temperaturerecord.model.UserRegister;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserRegisterRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRegisterRepository extends CrudRepository<UserRegister, Integer> {

    UserRegister findFirstByIdNo(String idNo);
}
