package de.budget.BudgetAndroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.dto.AmountTO;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.BudgetService.dto.ItemTO;
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
    private List<AmountTO> itemsCategoriesAmount;
    private List<AmountTO> incomeCategoriesAmount;
    private List<AmountTO> vendorsAmount;
    private boolean firstStart = true;
    private int initialDataCounter = 0;
    private double incomeLastPeriod = 0.0;
    private double lossLastPeriod = 0.0;

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

    public double getIncomeLastPeriod() {
        return this.incomeLastPeriod;
    }

    public void setIncomeLastPeriod(double income) {
        this.incomeLastPeriod = income;
    }

    public double getLossLastPeriod() {
        return this.lossLastPeriod;
    }

    public void setLossLastPeriod(double loss) {
        this.lossLastPeriod = loss;
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

    public List<CategoryTO> getCategoriesByFormat(boolean income){
        Iterator<CategoryTO> i = categories.iterator();
        List<CategoryTO> result = new ArrayList<>();
        while(i.hasNext()) {
            CategoryTO categoryTO = i.next();
            if(categoryTO.isIncome() == income) result.add(categoryTO);
        }
        return result;
    }

    public List<String> getCategoriesName() {

        Iterator i = categories.iterator();

        ArrayList<String> categoriesName = new ArrayList<>();

        while(i.hasNext()) {
            CategoryTO category = (CategoryTO) i.next();
            categoriesName.add(category.getName());
        }

        return categoriesName;
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

    public void deleteCategory(int categoryId){
        for(int i=0; i<categories.size();i++){
            if(categories.get(i).getId() == categoryId) {
                categories.remove(i);
            }
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

    public void deleteVendor(int vendorId){
        for(int i=0; i<vendors.size();i++){
            if(vendors.get(i).getId() == vendorId) {
                vendors.remove(i);
            }
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

    public void deleteIncome(int IncomeId){
        for(int i=0; i<income.size();i++){
            if(income.get(i).getId() == IncomeId) {
                income.remove(i);
            }
        }
    }

    // Basket Section

    public void setBasket(List<BasketTO> list){
        this.basket = list;
    }

    public List<BasketTO> getBasket(){
        return this.basket;
    }

    public BasketTO getBasketById(int id){
        ListIterator <BasketTO> li =  this.basket.listIterator();
        while(li.hasNext()) {
            BasketTO o = li.next();
            if (o.getId() == id) return o;
        }
        return null;
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

    public void deleteBasket(int basketId){
        for(int i=0; i<basket.size();i++){
            if(basket.get(i).getId() == basketId) {
                basket.remove(i);
            }
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


    // Items Section
    public void setItems(List< ItemTO> list){
        ListIterator <ItemTO> li = list.listIterator();
        while (li.hasNext()) {
            ItemTO item = li.next();
            BasketTO basket = getBasketById(item.getBasket());
            basket.setItem(item);
        }
    }

    public List<ItemTO> getItemsByBasket(int basketId){
        BasketTO basket = getBasketById(basketId);
        return basket.getItems();
    }



    public void setItemsCategoriesAmount(List<AmountTO> list){
        this.itemsCategoriesAmount = list;
    }

    public List<AmountTO> getItemsCategoriesAmount(){
        return this.itemsCategoriesAmount;
    }

    public List<AmountTO> getVendorsAmount() {
        return vendorsAmount;
    }

    public void setVendorsAmount(List<AmountTO> vendorsAmount) {
        this.vendorsAmount = vendorsAmount;
    }

    public List<AmountTO> getIncomeCategoriesAmount() {
        return incomeCategoriesAmount;
    }

    public void setIncomeCategoriesAmount(List<AmountTO> incomeCategoriesAmount) {
        this.incomeCategoriesAmount = incomeCategoriesAmount;
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
