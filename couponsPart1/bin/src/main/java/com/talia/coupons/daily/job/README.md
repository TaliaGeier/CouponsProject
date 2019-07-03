# `public class CouponExpirationDailyJob implements Runnable`

CouponExpirationDailyJob - creating a daily job which runs in the background using a thread and deletes all expired coupons from the DB.

## `@Override  public void run()`

run - starts the thread, the function deletes all expired coupons.

## `public void stop()`

stop - stops the thread.
