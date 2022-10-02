CREATE A WEB ORDER


1. Launch Chrome browser.
2. Navigate to http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx
3. Login using username Tester and password test
4. Click on Order link
5. Enter a random product quantity between 1 and 100
6. Click on Calculate and verify that the Total value is correct. 
   Price per unit is 100.  The discount of 8 % is applied to quantities of 10+.
   So for example, if the quantity is 8, the Total should be 800.
   If the quantity is 20, the Total should be 1840.
   If the quantity is 77, the Total should be 7084. And so on.

6. Generate and enter random first name and last name.
7. Generate and Enter random street address
8. Generate and Enter random city
9. Generate and Enter random state
10. Generate and Enter a random 5 digit zip code

EXTRA: As an extra challenge, for steps 6-10 download 1000 row of corresponding realistic data from mockaroo.com in a csv format and load it to your program and use the random set of data from there each time. 

11. Select the card type randomly. On each run your script should select a random type.
12. Generate and enter the random card number: 
      If Visa is selected, the card number should start with 4.
      If MasterCard is selected, card number should start with 5.
      If American Express is selected, card number should start with 3.
      Card numbers should be 16 digits for Visa and MasterCard, 15 for American Express.
13. Enter a valid expiration date (newer than the current date)
14. Click on Process
15. Verify that “New order has been successfully added” message appeared on the page.
16. Click on View All Orders link.
17. The placed order details appears on the first row of the orders table. Verify that the entire information contained on the row (Name, Product, Quantity, etc) matches the previously entered information in previous steps.
18. Log out of the application.

Push your code to GitHub, and share the repo link in the given repo.txt file