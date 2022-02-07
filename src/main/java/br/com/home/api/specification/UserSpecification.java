package br.com.home.api.specification;

import br.com.home.api.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serial;

public class UserSpecification {

    public static Specification<User> search(String text){
        return new Specification<User>(){

            @Serial
            private static final long serialVersionUID = -4453993331717769377L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(text == null || text.trim().length() <= 0) return null;

                String likeTerm = "%" + text + "%";

                Predicate predicate = criteriaBuilder.or(
                                            criteriaBuilder.like(
                                                    root.get("name"), likeTerm),
                                            criteriaBuilder.or(
                                                    criteriaBuilder.like(
                                                            root.get("email"), likeTerm)),
                                                    criteriaBuilder.like(root.get("role").as(String.class), likeTerm));

                return predicate;
            }
        };
    }

}
