package com.kenzie.appserver.repositories;


import com.kenzie.appserver.repositories.model.OrganizationRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrganizationRepository extends CrudRepository<OrganizationRecord, String> {


}
