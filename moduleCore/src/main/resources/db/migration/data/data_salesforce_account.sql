-- test record
insert into salesforce.account (
    code__c,
    login_code__c,
    password__c,
    name,
    hashed_code__c,
    login_screen_text__c
) values (
    'account001',
    'test',
    '$2a$08$kI8rQBeVkR1flO30Lj2UwuJssiem7quiJVPclECy8meSouH0JcMba', -- Test1234
    'test account',
    'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff',
    'login_screen_text__c
個人IDに関するメッセージが入る'
);
