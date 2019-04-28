# `public abstract class BasicAttributes`

BasicAttributes - An abstract class containing the basic attributes that the Company and Customer classes can inherit from.

## `public void setEmail(String email) throws ExEmail, ExNotNull`

setEmail - a function used to validate the email address entered answers to the requirements of the REGEX.

 * **Parameters:** `email` - the email entered.
 * **Exceptions:**
   * `ExEmail` - an error message for when the email address entered is invalid.
   * `ExNotNull` - an error message when the field has to be filled.

## `public void setId(int id) throws ExIdValidation`

setId - makes sure the ID entered is not null.

 * **Parameters:** `id` - the ID we entered.
 * **Exceptions:** `ExIdValidation` - an error message when entering invalid value for id fields.

## `public void setName(String name) throws ExNameLength, ExNotNull`

setName - makes sure the name isn't null.

 * **Parameters:** `name` - the name entered.
 * **Exceptions:**
   * `ExNameLength` - an error message when the name entered has to be longer.
   * `ExNotNull` - an error message when the field has to be filled.

## `public void setPassword(String password) throws ExPassword`

setPassword - a function used to make sure the password entered contains at least one number and starts with two letters.

 * **Parameters:** `password` - the password entered.
 * **Exceptions:** `ExPassword` - an error message when the password entered is invalid.

## `public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]`

Regular expression - ensures that the email entered is valid.
-----------------------

# `public class Company extends BasicAttributes`

Company - this class represents the attributes of the companies and inherits from the BasicAttributes class.

## `@Override  public String toString()`

toString - a function that prints all the company's information.



----------------------

# `public class Customer extends BasicAttributes`

Customer - this class represents the attributes of the customers and inherits from the BasicAttributes class.

## `public void setlastName(String lastName) throws ExNameLength, ExNotNull`

setlastName - makes sure the last name entered is not null and has at least two letters.

 * **Parameters:** `lastName` - the last name entered.
 * **Exceptions:**
   * `ExNameLength` - an error message when the name entered has to be longer.
   * `ExNotNull` - an error message when the field has to be filled.

## `@Override  public String toString()`

toString - a function that prints all the customer's information.

----------------------

# `public class Coupon`

Coupon - this class represents the attributes and functions necessary to create a coupon.

## `public void setId(int id) throws ExIdValidation`

setId - the function validates that the id entered is bigger than zero.

 * **Parameters:** `id` - the id of the coupon.
 * **Exceptions:** `ExIdValidation` - an error message when entering invalid value for id fields.

## `public void setCompanyId(int companyId) throws ExIdValidation`

setCompanyId - makes sure the companyId attribute is bigger than zero.

 * **Parameters:** `companyId` - the company's ID.
 * **Exceptions:** `ExIdValidation` - an error message when entering invalid value for id fields.

## `public void setTitle(String title) throws ExNotNull`

setTitle - makes sure the title entered isn't null.

 * **Parameters:** `title` - the title of the coupon.
 * **Exceptions:** `ExNotNull` - an error message when the field has to be filled.

## `public void setDescription(String description) throws ExNotNull`

setDescription - makes sure the description isn't null.

 * **Parameters:** `description` - the description of the coupon.
 * **Exceptions:** `ExNotNull` - an error message when the field has to be filled.

## `public void setEnd_date(Date end_date)`

setEnd_date - makes sure the end date we entered is after the start date and after the current date.

 * **Parameters:** `end_date` - the end date of the coupon.

## `public void setAmount(int amount) throws ExCantBeZero`

setAmount - makes sure the coupon amount is bigger than zero.

 * **Parameters:** `amount` - the coupon amount we want to create.
 * **Exceptions:** `ExCantBeZero` - an error message when the field needs to be bigger than 0.

## `public void setPrice(double price) throws ExCantBeZero`

setPrice - makes sure the coupon price is bigger than zero.

 * **Parameters:** `price` - the price of the coupon.
 * **Exceptions:** `ExCantBeZero` - an error message when the field needs to be bigger than 0.

## `@Override  public String toString()`

toString - a function that prints all the coupon's information.
-----------------------

# `public enum Category`

Category - is an enum, representing all categories.
