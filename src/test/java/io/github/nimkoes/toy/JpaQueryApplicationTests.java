package io.github.nimkoes.toy;

import io.github.nimkoes.toy.entity.A;
import io.github.nimkoes.toy.entity.B;
import io.github.nimkoes.toy.repository.ARepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class JpaQueryApplicationTests {

    @Autowired
    private EntityManager em;

    @Autowired
    private ARepository aRepository;

    @Test
    public void nPlusOneIssueTest() {
        // A와 B 데이터를 저장
        A a1 = new A();
        a1.setName("A1");
        em.persist(a1);

        A a2 = new A();
        a2.setName("A2");
        em.persist(a2);

        B b1 = new B();
        b1.setDescription("B1");
        b1.setA(a1);
        em.persist(b1);

        B b2 = new B();
        b2.setDescription("B2");
        b2.setA(a1);
        em.persist(b2);

        B b3 = new B();
        b3.setDescription("B3");
        b3.setA(a2);
        em.persist(b3);

        em.flush();
        em.clear();

        // A 엔티티를 조회하면서 Lazy 로딩이 된 B 엔티티에 접근할 때 N+1 발생 여부 확인
        List<A> aList = aRepository.findAll();

        for (A a : aList) {
            System.out.println("A 이름: " + a.getName());

            // Lazy 로딩으로 인해 B 엔티티에 접근할 때 추가 쿼리 발생
            for (B b : a.getBList()) {
                System.out.println("연관된 B 설명: " + b.getDescription());
            }
        }
    }

}
