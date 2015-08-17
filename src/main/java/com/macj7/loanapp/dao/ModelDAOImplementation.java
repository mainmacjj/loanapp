package com.macj7.loanapp.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.macj7.loanapp.model.User;
import com.macj7.loanapp.model.Customer;
import com.macj7.loanapp.model.LoanOfficer;
import com.macj7.loanapp.model.Loan;
import com.macj7.loanapp.model.Repayment;
import java.math.BigDecimal;
import java.util.Date;
import static org.hibernate.FlushMode.COMMIT;
import static org.hibernate.FlushMode.MANUAL;
import static org.hibernate.Hibernate.initialize;

/**
 *
 * @author macj7
 */
public class ModelDAOImplementation implements ModelDAO{
    
    Session session = null;

    public ModelDAOImplementation() {

    }
    
    
    @Override
    public boolean addCustomerUser(String name, String pcode) {
        this.session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;
        User user = new User();
        user.setPcode(pcode);
        user.setPermission(2);
        
        Customer cust = new Customer();
        cust.setCustName(name);
        cust.setUser(user);
        
        
        
        try {
            tx = session.beginTransaction();
            session.save(user);
            session.save(cust);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }

           throw e;
        }
        return true;
    }
    
    
    
    @Override
    public boolean addLoanOfficerUser(String name, String pcode) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user = new User();
        user.setPcode(pcode);
        user.setPermission(1);
        
        LoanOfficer officer = new LoanOfficer();
        officer.setOfficerName(name);
        officer.setUser(user);
        
        
        
        try {
            tx = session.beginTransaction();
            session.save(user);
            session.save(officer);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
           throw e;
        }
        return true;
    }
    
    
    @Override
    public boolean passCodeInUse(String pcode) {
        this.session = HibernateUtil.getSessionFactory().openSession();

        List<User> userList;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            
            String hql = "from User where pcode='"+pcode+"'";
            Query q = session.createQuery(hql);
            userList = q.list();

            if (userList.isEmpty()) {
                System.out.println("End false - Session State:"+session.isConnected());
                session.close();
                return false;
            }

        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return true;
    }
    
    
    
    @Override
    public User loginUser(String pcode) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        
        List<User> userList;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            
            String hql = "from User where pcode='"+pcode+"'";
            Query q = session.createQuery(hql);
            userList = q.list();
            
            if (userList.isEmpty()) {
                session.close();
                return null;
            }
            
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return userList.get(0);
    }
    
    
    
    @Override
    public User getUser(int id) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        User user = null;

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            user = (User)session.get(User.class, id); 

            if (user == null) {
                session.close();
                return null;
            }
           
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return user;
    }
    
    
    @Override
    public List<User> getUsers() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            String hql = "from User";
            Query q = session.createQuery(hql);
            userList = q.list();
            
            if (userList.isEmpty()) {
                session.close();
                return null;
            }
            
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return userList;
    }
    
    
    @Override
    public List<User> getUsers(int permissionFlag) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            String hql = "from User where permission ='"+permissionFlag+"'";
            Query q = session.createQuery(hql);
            userList = q.list();
            
            if (userList.isEmpty()) {
                session.close();
                return null;
            }
            
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return userList;
    }
    
    
    @Override
    public Loan getLoan(int id) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        Loan loan = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            loan = (Loan)session.get(Loan.class, id); 
            initialize(loan.getUserByCustIdNo());
            initialize(loan.getUserByOfficerIdNo());

        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return loan;
    }
    
    
    @Override
    public List<Loan> getLoans() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        List<Loan> loanList = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            Query q = session.createQuery("from Loan");
            loanList = q.list();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return loanList;
    }
    
    
    @Override
    public List<Loan> getLoans(int status) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        List<Loan> loanList = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(MANUAL);
            String hql = "from Loan where loan_status_id ='"+status+"'";
            Query q = session.createQuery(hql);
            loanList = q.list();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return loanList;
    }
    
    @Override
    public List<Loan> getLoans(int status, int id) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        List<Loan> loanList = null;
        Transaction tx = null;
        session.setFlushMode(MANUAL);
        System.out.println("Session is connected:"+session.isConnected());

        try {
            tx = this.session.beginTransaction();
            
            String hql = "from Loan where loan_status_id ='"+status+"' and cust_id_no='"+id+"'";
            Query q = session.createQuery(hql);
            loanList = q.list();
            for (Loan loan : loanList) {
               initialize(loan.getUserByCustIdNo());
               initialize(loan.getUserByOfficerIdNo());
               initialize(loan.getRepayment());
            }
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        return loanList;
    }
    
    
    @Override
    public boolean addLoan(int loan_status_id, User officer, User customer, BigDecimal loan_amt) {
        this.session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;
        
        Loan loan = new Loan();
        loan.setLoanStatusId(loan_status_id);
        loan.setUserByOfficerIdNo(officer);
        loan.setUserByCustIdNo(customer);
        loan.setLoanAmt(loan_amt);
        
        
        try {
            tx = session.beginTransaction();
            session.setFlushMode(COMMIT);
            session.save(loan);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            // Need to log the error...
            //throw e;
            return false;
        }
        return true;
    }
    
    
    @Override
    public boolean addRepayment(Loan loan, BigDecimal amt_repaid, Date date_repaid) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Repayment> records = null;
      
        

        try {
            tx = session.beginTransaction();
            
            session.setFlushMode(COMMIT);
            
            Repayment repayment = (Repayment)session.get(Repayment.class, loan.getLoanNo()); 
            
            if (repayment != null) {
                session.close();
                return false;
            }
            
            repayment = new Repayment(); 
  
            repayment.setLoan(loan);
            repayment.setAmtRepaid(amt_repaid);
            repayment.setDateRepaid(date_repaid);

            BigDecimal balance = loan.getLoanAmt().subtract(amt_repaid);
            int result = balance.compareTo(BigDecimal.ZERO);
            if (result == 0 || result == -1) {
                loan.setLoanStatusId(2);
            }
            loan.setLoanAmt(balance);
            
            session.save(repayment);
            session.update(loan);
            
            tx.commit();
       
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        return true;
    }
    
    
    @Override
    public boolean updateRepayment(Loan loan, BigDecimal new_payment, Date date_repaid) {
        this.session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.setFlushMode(COMMIT);
            Repayment repayment = (Repayment)session.get(Repayment.class, loan.getLoanNo()); 

            if (repayment == null) {
                session.close();
                System.out.println("[P]:"+"records is empty...go figure.");
                return false;
            }
            
            //repayment = records.get(0);
            
            BigDecimal repaid = repayment.getAmtRepaid().add(new_payment);
            repayment.setAmtRepaid(repaid);
            
            BigDecimal balance = loan.getLoanAmt().subtract(new_payment);
            
            int result = balance.compareTo(BigDecimal.ZERO);
            if (result == 0 || result == -1) {
                loan.setLoanStatusId(2);
            }
            
            loan.setLoanAmt(balance);
            
            session.update(repayment);
            session.update(loan);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        return true;
    }
    
    
    @Override
    public boolean deleteRepayment(Loan loan) {
        this.session = HibernateUtil.getSessionFactory().openSession();


        Transaction tx = null;
        System.out.println("Session is connected:"+session.isConnected());
        try {
            tx = session.beginTransaction();
            session.setFlushMode(COMMIT);
            Repayment repayment = (Repayment)session.get(Repayment.class, loan.getLoanNo()); 

            
            if (repayment == null) {
                session.close();
                return false;
            }
            
            
            BigDecimal repaid = repayment.getAmtRepaid();
            
            BigDecimal balance = loan.getLoanAmt().add(repaid);
            
            int result = balance.compareTo(BigDecimal.ZERO);
            if (result == 1) {
                loan.setLoanStatusId(1);
            }
            
            
            loan.setLoanAmt(balance);
            
            session.update(loan);
            session.delete(repayment);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw e;
        }
        return true;
    }
    
}
