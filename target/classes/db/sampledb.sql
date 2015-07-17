CREATE MEMORY TABLE SAMPLE(ID VARCHAR(16) NOT NULL PRIMARY KEY,NAME VARCHAR(50),DESCRIPTION VARCHAR(100),USE_YN CHAR(1),REG_USER VARCHAR(10))
CREATE MEMORY TABLE IDS(TABLE_NAME VARCHAR(16) NOT NULL PRIMARY KEY,NEXT_ID DECIMAL(30) NOT NULL)
SET SCHEMA PUBLIC
INSERT INTO SAMPLE VALUES('SAMPLE-00001','Runtime Environment','Foundation Layer','Y','eGov')
INSERT INTO SAMPLE VALUES('SAMPLE-00002','Runtime Environment','Persistence Layer','Y','eGov')
INSERT INTO SAMPLE VALUES('SAMPLE-00003','Runtime Environment','Presentation Layer','Y','eGov')
INSERT INTO SAMPLE VALUES('SAMPLE-00004','Runtime Environment','Business Layer','Y','eGov')
INSERT INTO SAMPLE VALUES('SAMPLE-00005','Runtime Environment','Batch Layer','Y','eGov')
INSERT INTO SAMPLE VALUES('SAMPLE-00006','Runtime Environment','Integration Layer','Y','eGov')
INSERT INTO IDS VALUES('SAMPLE',7)


CREATE MEMORY TABLE MEMBERINFO(ID VARCHAR(50) NOT NULL PRIMARY KEY, PASSWORD VARCHAR(300), NAME VARCHAR(30))
CREATE MEMORY TABLE AUTHORITY(AUTHORITY VARCHAR(50), AUTHORITY_NAME VARCHAR(50))
CREATE MEMORY TABLE MEMBER_AUTHORITY(ID VARCHAR(50), AUTHORITY VARCHAR(50))
CREATE MEMORY TABLE GROUPS(ID VARCHAR(50), GROUP_NAME VARCHAR(50))
CREATE MEMORY TABLE GROUPS_MEMBER(GROUP_ID VARCHAR(50), MEMBER_ID VARCHAR(50))
CREATE MEMORY TABLE GROUPS_AUTHORITY(GROUP_ID VARCHAR(50), AUTHORITY VARCHAR(50))
CREATE MEMORY TABLE SECURED_RESOURCE(RESOURCE_ID VARCHAR(10), RESOURCE_NAME VARCHAR(50), RESOURCE_PATTERN VARCHAR(100), RESOURCE_TYPE VARCHAR(10), SORT_ORDER VARCHAR(10))
CREATE MEMORY TABLE SECURED_RESOURCE_AUTHORITY(RESOURCE_ID VARCHAR(10), AUTHORITY VARCHAR(50), NAME VARCHAR(30))