CREATE SCHEMA IF NOT EXISTS salesforce;
DROP TABLE IF EXISTS "salesforce"."account";

CREATE TABLE salesforce.account (
	id serial NOT NULL,
	sfid varchar(18) NULL,
	code__c varchar(18) NULL,
	parent_account_code__c varchar(255) NULL,
	login_code__c varchar(255) NULL,
	password__c varchar(255) NULL,
	"name" varchar(60) NULL,
	name_kana__c varchar(100) NULL,
	name_en__c varchar(100) NULL,
	hashed_code__c varchar(255) NULL,
	login_screen_text__c text,
	isdeleted bool NULL,
	systemmodstamp timestamp NULL,
	"_hc_lastop" varchar(32) NULL,
	"_hc_err" text NULL,
	created_by__c float8 NULL,
	created_at__c timestamp NULL,
	updated_by__c float8 NULL,
	updated_at__c timestamp NULL,
	version__c float8 NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id)
);
CREATE INDEX hc_idx_account_systemmodstamp ON salesforce.account USING btree (systemmodstamp);
CREATE UNIQUE INDEX hcu_idx_account_code__c ON salesforce.account USING btree (code__c);
CREATE UNIQUE INDEX hcu_idx_account_hashed_code__c ON salesforce.account USING btree (hashed_code__c);
CREATE UNIQUE INDEX hcu_idx_account_sfid ON salesforce.account USING btree (sfid);

COMMENT ON COLUMN "salesforce"."account"."id" IS 'id';
COMMENT ON COLUMN "salesforce"."account"."sfid" IS 'sfid';
COMMENT ON COLUMN "salesforce"."account"."code__c" IS 'コード';
COMMENT ON COLUMN "salesforce"."account"."parent_account_code__c" IS '親取引先コード';
COMMENT ON COLUMN "salesforce"."account"."login_code__c" IS 'ログインコード';
COMMENT ON COLUMN "salesforce"."account"."password__c" IS 'パスワード';
COMMENT ON COLUMN "salesforce"."account"."name" IS '名前';
COMMENT ON COLUMN "salesforce"."account"."name_kana__c" IS '名前（カナ）';
COMMENT ON COLUMN "salesforce"."account"."name_en__c" IS '名前（英）';
COMMENT ON COLUMN "salesforce"."account"."hashed_code__c" IS 'ハッシュ化コード';
COMMENT ON COLUMN "salesforce"."account"."login_screen_text__c" IS 'ログイン画面テキスト';

COMMENT ON TABLE  "salesforce"."account" IS '取引先';
