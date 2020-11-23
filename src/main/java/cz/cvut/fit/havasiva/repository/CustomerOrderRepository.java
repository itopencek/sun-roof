package cz.cvut.fit.havasiva.repository;

import cz.cvut.fit.havasiva.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
    Optional<CustomerOrder> findByMadeBy(String madeBy);

    List<CustomerOrder> findByOrderedFrom(int OrderedFrom);
}
