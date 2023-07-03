package com.example.demo.service

import com.example.demo.exception.EmployeeNotFoundException
import com.example.demo.repository.EmployeeRepository
import com.example.demo.repository.model.Employee
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val employeeRepository: EmployeeRepository) {

    fun getAllEmployees(): List<Employee> = employeeRepository.findAll()

    fun getEmployeesById(employeeId: Long): Employee = employeeRepository.findById(employeeId)
        .orElseThrow { EmployeeNotFoundException(HttpStatus.NOT_FOUND, "No matching employee was found") }

    fun createEmployee(employee: Employee): Employee = employeeRepository.save(employee)

    fun updateEmployeeById(employeeId: Long, employee: Employee): Employee {
        return if (employeeRepository.existsById(employeeId)) {
            employeeRepository.save(
                Employee(
                    id = employee.id,
                    userName = employee.userName,
                    firstName = employee.firstName,
                    middleName = employee.middleName,
                    lastName = employee.lastName,
                    emailId = employee.emailId,
                    dayOfBirth = employee.dayOfBirth
                )
            )
        } else throw EmployeeNotFoundException(HttpStatus.NOT_FOUND, "No matching employee was found")
    }

    fun deleteEmployeesById(employeeId: Long) {
        return if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId)
        } else throw EmployeeNotFoundException(HttpStatus.NOT_FOUND, "No matching employee was found")
    }
}