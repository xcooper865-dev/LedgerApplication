My name is Xavier Cooper, and I am currently enrolled in the Year Up United program under the Application Development Pathway track. This is my first README file, created as part of my coursework.

The project presented here is a fully functional Account Ledger application, designed to record, manage, and display financial transactions such as deposits and payments through a user-friendly command-line interface.









A) B) C) D)


A — Add Deposit
Prompt the user for deposit details and append the transaction to the CSV file. Deposits should be positive amounts.

B — Make Payment (Debit)
Prompt the user for payment/debit details and append the transaction to the CSV file. Payments should be negative amounts.

C — Ledger
Go to the Ledger screen (described below).

D — Exit
Exit the application.
 
 ![Home Screen](https://github.com/user-attachments/assets/54705ec6-f133-4d83-a435-9872bf143eb7)


Option A giving the user a prompt to enter the date,time, the description of the deposit,the vendor its coming from and the deposit amount 



 ![New Note](https://github.com/user-attachments/assets/fc24e020-584f-48de-9173-1a1ace69815c)


Option B Prompts the user for payment/debit details the payments should be negitive 


 ![New Note](https://github.com/user-attachments/assets/1c421699-395c-453b-b72c-29050aa82663)


Option C take user to the ledger menu 

A — All
Display all entries (sorted newest → oldest).

D — Deposits
Display only deposit entries (amount >= 0).

P — Payments
Display only payment entries (amount < 0).

R — Reports (opens the Reports screen — see below)

H — Home
Return to the Home screen.
 ![New Note](https://github.com/user-attachments/assets/50ee0541-5ab2-402e-ae16-1e28dd7620a8)



Option A on the ledger menu allows for the user to see all transactions credit and debt from the account
 ![New Note](https://github.com/user-attachments/assets/183e720c-76e4-43ba-8f13-07166e118455)

Option D allows for the user to see all the deposits coming into the account 

 ![New Note](https://github.com/user-attachments/assets/c3ad39e0-6b80-43f1-9d98-e42f8ed9a895)


Option P allows for the user to see all the paymen leaving the accout


1-Month To Date
Show transactions from the 1st day of the current month through today.

2 — Previous Month
Show transactions from the full previous calendar month.

3 — Year To Date
Show transactions from January 1 of the current year through today.

4 — Previous Year
Show transactions from the full previous calendar year.

5 — Search by Vendor
Prompt the user for a vendor string and display all transactions whose vendor matches (exact or partial match, implementation choice). Match should be case-insensitive for usability.

0 — Back
Return to the Ledger page.

 ![New Note](https://github.com/user-attachments/assets/7fd2eaf6-99e5-4655-aef1-6e288c437804)


My code that i would like to share is the DisplayTransaction i used one method to filter through the payments and deposits 

 ![New Note](https://github.com/user-attachments/assets/4afe55d7-898c-42c4-9a7e-655e43dea659)







