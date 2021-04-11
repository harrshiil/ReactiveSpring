package com.react.ReactDemo;

import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class ReactDemoApplication {

    public static void main(String[] args) {
        Observable<User> userObservable = new ReactDemoApplication().getData(getUsers());
        userObservable.subscribe(System.out::println,
                throwable -> System.out.println("Exception: " + throwable),
                () -> System.out.println("Completed"));

    }

    private static List<User> getUsers() {
        return Arrays.asList(new User("Harshil", 90000L), new User("Sam", 50000L));
    }

    Observable<User> getData(List<User> userList) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                userList.forEach(user -> {
                    subscriber.onNext(user);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    subscriber.onError(new RuntimeException("wow exception"));
                });
            }
            subscriber.onCompleted();
        });
    }

    static class User {
        private final String name;
        private final Long salary;

        public User(String name, Long salary) {
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

}
