package com.macj7.loanapp.service;

import com.macj7.loanapp.dao.ModelDAOImplementation;
import com.macj7.loanapp.model.Loan;
import com.macj7.loanapp.model.User;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 *
 * @author macj7
 */
public class SimpleLoanManager implements LoanManager {
    private ModelDAOImplementation dao = null;
    
    public SimpleLoanManager() {
        this.dao = new ModelDAOImplementation();
    }
    
    @Override
    public boolean registerUser(String name, String pcode, int permission) {
        if (permission == 1) {
            return dao.addLoanOfficerUser(name, pcode);
        }
        else {
            return dao.addCustomerUser(name, pcode);
        }
    }
    
    
    
    @Override
    public boolean passCodeInUse(String pcode) {
        return dao.passCodeInUse(pcode);
    }
    
    
    @Override
    public User loginUser(String pcode) {
        return dao.loginUser(pcode);
    }
    
    @Override
    public User getUser(int id) {
        return dao.getUser(id);
    }
    
    
    @Override
    public List<User> getCustomers() {
        return dao.getUsers(2);
    }
    
    
    @Override
    public Loan getLoan(int id) {
        return dao.getLoan(id);
    }
    
    
    @Override
    public List<Loan> getActiveLoans() {
        return dao.getLoans(1);
    }
    
    
    @Override
    public List<Loan> getActiveLoans(int id) {
        return dao.getLoans(1,id);
    }
    
    
    @Override
    public boolean createLoan(int loan_status_id, User officer, User cust, BigDecimal loan_amt) {
        return dao.addLoan(loan_status_id, officer, cust, loan_amt);
    }
    
    
    @Override
    public boolean createRepayment(Loan loan, BigDecimal amt_repaid) {
        Date date_repaid = new Date();
        return dao.addRepayment(loan, amt_repaid, date_repaid);
    }
    
    
    @Override
    public boolean updateRepayment(Loan loan, BigDecimal new_payment) {
        Date date_repaid = new Date();
        return dao.updateRepayment(loan, new_payment, date_repaid);
    }
    
    
    @Override
    public boolean deleteRepayment(Loan loan) {
        return dao.deleteRepayment(loan);
    }
}
