package de.budget.BudgetAndroid;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.ListIterator;

import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.BudgetService.dto.ObjectTO;
import de.budget.BudgetService.dto.PaymentTO;
import de.budget.BudgetService.dto.VendorTO;

/**
 * @Author Christopher
 * @date 01.06.2015
 */
public class BudgetAndroidApplication extends Application{
    private int sessionId;
    private BudgetOnlineService budgetOnlineService;
    private List<CategoryTO> categories;
    private List<VendorTO> vendors;
    private List<PaymentTO> payments;
    private List<IncomeTO> income;
    private List<BasketTO> basket;
    private boolean firstStart = true;
    private int initialDataCounter = 0;

    public BudgetAndroidApplication() {
        this.budgetOnlineService = new BudgetOnlineServiceImpl();
    }

    public BudgetOnlineService getBudgetOnlineService() {
        return this.budgetOnlineService;
    }

    public void increaseInitialDataCounter(){
        if(firstStart) {
            this.initialDataCounter++;
            if (initialDataCounter == 5) {
                firstStart = false;
                Log.d("INFO", "Initiales Datenladen abgeschlossen.");
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
            }
        }
    }

    public boolean getFirstStart(){
        return this.firstStart;
    }
    // User Section

    public int getSession() {
        return this.sessionId;
    }

    public void setSession(int sessionId) {
        this.sessionId = sessionId;
    }

    // Categories Section

    public void setCategories(List<CategoryTO> list){
        this.categories = list;
    }

    public List<CategoryTO> getCategories(){
        return this.categories;
    }

    public CategoryTO getCategory(int categoryId){
        CategoryTO result = null;
        for(int i=0; i< categories.size();i++){
            if(categories.get(i).getId() == categoryId) {
                result =  categories.get(i);
            }
        }
        return result;
    }


    public void checkCategoriesList(CategoryTO newCategory){
        boolean found = false;
        for(int i=0; i<categories.size();i++){
            if(categories.get(i).getId() == newCategory.getId()) {
                categories.remove(i);
                categories.add(newCategory);
                found = true;
            }
        }
        if(!found){
            categories.add(newCategory);
        }
    }

    // Vendors Section

    public void setVendors(List<VendorTO> list){
        this.vendors = list;
    }

    public List<VendorTO> getVendors(){
        return this.vendors;
    }

    public VendorTO getVendorById(int id) {
        ListIterator <VendorTO> li = vendors.listIterator();
        while (li.hasNext()) {
            VendorTO vendor = li.next();
            if (vendor.getId() == id) return vendor;
        }
        return null;
    }

    public ObjectTO getObjectTObyId (int id, List<ObjectTO> list) {
        ListIterator <ObjectTO> li =  list.listIterator();
        while(li.hasNext()) {
            ObjectTO o = li.next();
            if (o.getId() == id) return o;
        }
        return null;
    }

    public void checkVendorsList(VendorTO newVendor){
        boolean found = false;
        for(int i=0; i<vendors.size();i++){
            if(vendors.get(i).getId() == newVendor.getId()) {
                vendors.remove(i);
                vendors.add(newVendor);
                found = true;
            }
        }
        if(!found){
            vendors.add(newVendor);
        }
    }

    // Income Section

    public void setIncome(List<IncomeTO> list){
        this.income = list;
    }

    public List<IncomeTO> getIncome(){
        return this.income;
    }


    public void checkIncomeList(IncomeTO newIncome){
        boolean found = false;
        for(int i=0; i<income.size();i++){
            if(income.get(i).getId() == newIncome.getId()) {
                income.remove(i);
                income.add(newIncome);
                found = true;
            }
        }
        if(!found){
            income.add(newIncome);
        }
    }

    // Basket Section

    public void setBasket(List<BasketTO> list){
        this.basket = list;
    }

    public List<BasketTO> getBasket(){
        return this.basket;
    }


    public void checkBasketList(BasketTO newBasket){
        boolean found = false;
        for(int i=0; i<basket.size();i++){
            if(basket.get(i).getId() == newBasket.getId()) {
                basket.remove(i);
                basket.add(newBasket);
                found = true;
            }
        }
        if(!found){
            basket.add(newBasket);
        }
    }

    // Payment Section
    public void setPayments(List<PaymentTO> list){
        this.payments = list;
    }

    public List<PaymentTO> getPayments(){
        return this.payments;
    }

    public PaymentTO getPaymentById(int id) {
        ListIterator <PaymentTO> li = payments.listIterator();
        while (li.hasNext()) {
            PaymentTO payment = li.next();
            if (payment.getId() == id) return payment;
        }
        return null;
    }

    public PaymentTO getPaymentByName(String name){
        PaymentTO result = null;
        for(int i=0; i<payments.size();i++){
            if(name.equals(payments.get(i).getName())) {
                result = payments.get(i);
            }
        }
        return result;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.categories = null;
        this.vendors = null;
        this.payments = null;
        this.sessionId = -99;
        this.budgetOnlineService = null;
        this.initialDataCounter = 0;
        this.firstStart = true;
    }

    public void reset() {
        super.onTerminate();
        this.categories = null;
        this.vendors = null;
        this.payments = null;
        this.sessionId = -99;
        this.initialDataCounter = 0;
        this.firstStart = true;
    }
}
