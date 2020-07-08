package application.data.repository;

import application.data.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Transactional(readOnly = true)
    @Query("select ur from dbo_user_role ur where ur.userId = :id")
    Iterable<UserRole> findRolesOfUser(@Param("id") int userId);
}
