package com.xdlr.temperaturerecord.repo;

import org.springframework.data.repository.CrudRepository;
import com.xdlr.temperaturerecord.model.TemperatureRecord;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TemperatureRecordRepository
// CRUD refers Create, Read, Update, Delete

public interface TemperatureRecordRepository extends CrudRepository<TemperatureRecord, Integer> {

}
