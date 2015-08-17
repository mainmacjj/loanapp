package com.macj7.loanapp.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.macj7.loanapp.model.User;
import com.macj7.loanapp.model.Loan;

/**
 *
 * @author macj7
 */
public interface ModelDAO {
    
    public boolean addCustomerUser(String name, String pcode);
    public boolean addLoanOfficerUser(String name, String pcode);
    public boolean passCodeInUse(String pcode);
    public User loginUser(String pcode);
    public User getUser(int id);
    public List<User> getUsers();
    public List<User> getUsers(int permission);
    public Loan getLoan(int id);
    public List<Loan> getLoans();
    public List<Loan> getLoans(int status);
    public List<Loan> getLoans(int status, int id);
    public boolean addLoan(int loan_status_id, User officer, User customer, BigDecimal loan_amt);
    public boolean addRepayment(Loan loan, BigDecimal amt_repaid, Date date_repaid);
    public boolean updateRepayment(Loan loan, BigDecimal new_payment, Date date_repaid);
    public boolean deleteRepayment(Loan loan);
    
}
