package com.thanhhai.demo.repository;

import com.thanhhai.demo.entity.Role;
import com.thanhhai.demo.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    Optional<List<Role>> findByNameIn(List<ERole> eRoles);

}