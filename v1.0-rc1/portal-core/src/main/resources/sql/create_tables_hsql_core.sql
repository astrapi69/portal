CREATE TABLE core_box
(
   id int NOT NULL,
   created_at timestamp,
   created_by varchar(30),
   modified_at timestamp,
   modified_by varchar(30),
   box_type varchar(255),
   content varchar(10000),
   sort int,
   title varchar(255),
   PRIMARY KEY(id)
)
;
CREATE TABLE core_configuration
(
   conf_key varchar(255) NOT NULL,
   conf_description varchar(255),
   conf_group varchar(255),
   conf_type varchar(255),
   conf_value varchar(255) NOT NULL,
   PRIMARY KEY(conf_key)
)
;
CREATE TABLE core_email_tpl
(
   id int NOT NULL,
   created_at timestamp,
   created_by varchar(30),
   modified_at timestamp,
   modified_by varchar(30),
   content varchar(10000),
   subject varchar(255),
   PRIMARY KEY(id)
)
;
CREATE TABLE core_right
(
   right_id varchar(50) NOT NULL,
   created_at timestamp,
   created_by varchar(30),
   modified_at timestamp,
   modified_by varchar(30),
   description varchar(255),
   PRIMARY KEY(right_id)
)
;
CREATE TABLE core_role
(
   id int NOT NULL,
   created_at timestamp,
   created_by varchar(30),
   modified_at timestamp,
   modified_by varchar(30),
   active bit NOT NULL,
   description varchar(255),
   PRIMARY KEY(id)
)
;
CREATE TABLE core_role_right_xref
(
   role_id int NOT NULL,
   right_id varchar(50) NOT NULL
)
;
CREATE TABLE core_user
(
   id int NOT NULL,
   active bit NOT NULL,
   birthday timestamp,
   changed_at timestamp,
   confirm_app_at timestamp,
   confirmation_code varchar(255),
   confirm_req_at timestamp,
   confirmed bit NOT NULL,
   email varchar(100) NOT NULL,
   enable_contact_form bit,
   firstname varchar(100),
   forgot_code varchar(255),
   last_ip varchar(15),
   last_login_at timestamp,
   lastname varchar(100),
   password varchar(255) NOT NULL,
   reg_date timestamp NOT NULL,
   session_id varchar(255),
   username varchar(30),
   role_id int NOT NULL,
   PRIMARY KEY(id)
)
;

CREATE TABLE core_module_link
(
   link_type int NOT NULL,
   page_name varchar(255) NOT NULL,
   created_at timestamp,
   created_by varchar(30),
   modified_at timestamp,
   modified_by varchar(30),
   module_name varchar(255) NOT NULL,
   sort int NOT NULL,
   visible bit NOT NULL,
   PRIMARY KEY (link_type,page_name)
);