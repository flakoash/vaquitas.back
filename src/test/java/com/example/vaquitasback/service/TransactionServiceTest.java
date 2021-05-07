package com.example.vaquitasback.service;


import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.TransactionRepository;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.web.ForbiddenException;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceTest.class);
    @Autowired
    Validator validator;
    @Autowired
    ApplicationUserDetailsService userDetailsService;

    @Mock
    GroupService groupService;

    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void init() {
        transactionService = new TransactionService(transactionRepository, groupService, userRepository);
    }

    @Test
    public void getTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);

        User owner = new User(1, "testUser", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));


        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));
        List<Transaction> listTr = new ArrayList<>();
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);
        listTr.add(tr);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser"); // user id 1
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // userRepo
        when(userRepository.findByUsername("testUser") ).thenReturn(owner);

        // userRepo
        when(groupService.getMembers(1) ).thenReturn(new ArrayList<User>(Arrays.asList(owner)));

        // transactionRepo
        when(transactionRepository.getTransactionsByGroup_idOrderByCreatedAtDesc(1) ).thenReturn(listTr);

        //test
        Iterable<Transaction> transactionList = transactionService.getTransactions(1);

        assertEquals(1, StreamSupport.stream(transactionList.spliterator(), false).count());
    }

    @Test
    public void otherAuthUserGetTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);

        User authUser = new User(1, "testUser", "test user", "1234567", "None");
        User owner = new User(2, "testUser2", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));


        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));
        List<Transaction> listTr = new ArrayList<>();
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);
        listTr.add(tr);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser"); // user id 1
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // userRepo
        when(userRepository.findByUsername("testUser") ).thenReturn(authUser);

        // userRepo
        when(groupService.getMembers(1) ).thenReturn(new ArrayList<User>(Arrays.asList(owner)));

        // transactionRepo
        when(transactionRepository.getTransactionsByGroup_idOrderByCreatedAtDesc(1) ).thenReturn(listTr);

        //test
        Exception exception = assertThrows(ForbiddenException.class, () -> {
            transactionService.getTransactions(1);
        });
    }

    @Test
    public void addTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);
        User owner = new User(1, "testUser", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));


        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));

        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);

        //test
        transactionService.addTransaction(tr);

        verify(transactionRepository, times(1)).save(tr);
    }

    @Test
    public void addValidTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);

        User owner = new User(1, "testUser", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));

        // transaction
        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser");
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // test
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tr);

        assertEquals(0, violations.size());
    }

    @Test
    public void addInvalidAmountsTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);
        BigDecimal amount2 = new BigDecimal(101);

        User owner = new User(1, "testUser", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));

        // transaction
        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount2)));
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser");
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // test
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tr);

        assertEquals(1, violations.size());
        ConstraintViolation<?> violation = violations.iterator().next();
        assertEquals("Involved amounts dont sum up total amount..", violation.getMessage() );
    }

    @Test
    public void addNotMemberTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);

        User owner = new User(1, "testUser", "test user", "1234567", "None");
        Group group = new Group(2, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));

        // transaction
        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser");
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // test
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tr);

        assertEquals(1, violations.size());
        ConstraintViolation<?> violation = violations.iterator().next();
        assertEquals("Only members of the group can add transactions", violation.getMessage() );
    }

    @Test
    public void OwnerDiffThanAuthUserAddTransactionsTest() {
        BigDecimal amount = new BigDecimal(100);

        User owner = new User(2, "testUser", "test user", "1234567", "None");
        Group group = new Group(1, "testGroup", "None", new ArrayList<User>(Arrays.asList(owner)));

        // transaction
        List<Involved> invList =  new ArrayList<>(Arrays.asList(new Involved(1, owner, amount)));
        Transaction tr = new Transaction(1, "Test transaction", "test description", amount, owner, group);
        tr.setInvolved(invList);

        // Auth user
        UserDetails userDetails = userDetailsService.loadUserByUsername ("testUser");
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // test
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tr);

        assertEquals(1, violations.size());
//        ConstraintViolation<?> violation = violations.iterator().next();
//        assertEquals("Only members of the group can add transactions", violation.getMessage() );
    }
}