DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
	"id" Serial NOT NULL,
	"sfid" Character Varying( 18 ),
	"account_code" Character Varying( 10 ) NOT NULL,
	"login_id" Character Varying( 255 ) NOT NULL,
	"password" Character Varying( 255 ) NOT NULL,
	"temporary_password" Character Varying( 255 ),
	"password_reminder_token" Character Varying( 255 ),
	"password_first_updated" Boolean DEFAULT false NOT NULL,
	"last_name" Character Varying( 255 ) NOT NULL,
	"first_name" Character Varying( 255 ),
	"last_name_kana" Character Varying( 255 ),
	"first_name_kana" Character Varying( 255 ),
	"employee_number" Character Varying( 255 ),
	"mail_address" Character Varying( 255 ),
	"sex" Character Varying( 255 ),
	"sex_type" Character Varying( 255 ),
	"admin" Boolean DEFAULT false NOT NULL,
	"language" Character Varying( 255 ),
	"secret_question" Integer,
	"secret_question_answer" Character Varying( 255 ),
	"login_failed_count" Integer,
	"account_lock_limit_at" Timestamp Without Time Zone,
	"deleted_at" Timestamp Without Time Zone,
	"created_at" Timestamp Without Time Zone DEFAULT now() NOT NULL,
	"created_by" Integer,
	"updated_at" Timestamp Without Time Zone,
	"updated_by" Integer,
	"version" Integer DEFAULT 1 NOT NULL,
	PRIMARY KEY ( "id" ),
	CONSTRAINT "unique_user_account_code_login_id" UNIQUE( "account_code", "login_id" ) );
 ;

COMMENT ON COLUMN "public"."user"."id" IS 'id';
COMMENT ON COLUMN "public"."user"."sfid" IS 'sfid';
COMMENT ON COLUMN "public"."user"."account_code" IS '企業コード';
COMMENT ON COLUMN "public"."user"."login_id" IS 'ログインID';
COMMENT ON COLUMN "public"."user"."password" IS 'パスワード';
COMMENT ON COLUMN "public"."user"."temporary_password" IS '一時パスワード（ランダム生成パスワードなどを暗号化せず入れる）';
COMMENT ON COLUMN "public"."user"."password_reminder_token" IS 'パスワードリマインダートークン';
COMMENT ON COLUMN "public"."user"."password_first_updated" IS 'パスワード更新済み';
COMMENT ON COLUMN "public"."user"."last_name" IS '姓';
COMMENT ON COLUMN "public"."user"."first_name" IS '名';
COMMENT ON COLUMN "public"."user"."last_name_kana" IS 'セイ';
COMMENT ON COLUMN "public"."user"."first_name_kana" IS 'メイ';
COMMENT ON COLUMN "public"."user"."employee_number" IS '社員番号';
COMMENT ON COLUMN "public"."user"."mail_address" IS 'メールアドレス';
COMMENT ON COLUMN "public"."user"."sex" IS '性別';
COMMENT ON COLUMN "public"."user"."sex_type" IS '生物学的性別
男: MALE
女: FEMALE';
COMMENT ON COLUMN "public"."user"."admin" IS '管理者';
COMMENT ON COLUMN "public"."user"."language" IS '言語';
COMMENT ON COLUMN "public"."user"."secret_question" IS '秘密の質問';
COMMENT ON COLUMN "public"."user"."secret_question_answer" IS '秘密の質問回答';
COMMENT ON COLUMN "public"."user"."login_failed_count" IS 'ログイン失敗回数';
COMMENT ON COLUMN "public"."user"."account_lock_limit_at" IS 'アカウントロック解除日時';
COMMENT ON COLUMN "public"."user"."deleted_at" IS '削除日時';
COMMENT ON TABLE  "public"."user" IS 'ユーザー';

CREATE INDEX "index_account_code1" ON "public"."user" USING btree( "account_code" );
