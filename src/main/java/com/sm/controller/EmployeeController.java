package com.sm.controller;

import com.sm.config.CustomKeyGenerator;
import com.sm.model.Employee;
import com.sm.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j
@CacheConfig(cacheNames = {"employees"})
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    //@Cacheable – Method code is NOT executed if item found in cache.
    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    // For key Generator : when we see cache/data/employees endpoint,
    // it will show with classname_methodname_{id} as a key
    //@Cacheable(keyGenerator = "customKeyGenerator")
    public ResponseEntity<Optional<Employee>> find(@PathVariable(value = "id") Long id) {
        log.info("Employee data fetched from database:: " + id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee);
        }
        throw new RuntimeException("No id found");
    }

    //@CachePut – Method code is always executed and cache is updated after method execution.
    @PutMapping("/{id}")
    @CachePut(key = "#id")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id, @RequestBody Employee employeeDetails) {

        Optional<Employee> employee = employeeRepository.findById(id);
        employee.get().setName(employeeDetails.getName());
        final Employee updatedEmployee = employeeRepository.save(employee.get());
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id")
    //@CacheEvict(key = "#id", allEntries = true)
    // before invocation of this method, entire employees cache should be cleared (since most of the employees records might be updated on import, and cache might get stale).
    // @CacheEvict(value = "employees", beforeInvocation = true)
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(value = "id") Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new RuntimeException("No id found");
    }
}
