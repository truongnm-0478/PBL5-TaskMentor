CREATE TABLE users (
  "id" SERIAL PRIMARY KEY,
  "email" text UNIQUE NOT NULL,
  "username" text UNIQUE NOT NULL,
  "password" text NOT NULL,
  "role" int NOT NULL,
  "name" text,
  "phone" text,
  "delete_time" timestamp,
  "delete_by" int
);

CREATE TABLE teachers (
  "id" SERIAL PRIMARY KEY,
  "user_id" int NOT NULL,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("user_id") REFERENCES "users" ("id")
);

CREATE TABLE students (
  "id" SERIAL PRIMARY KEY,
  "user_id" int NOT NULL,
  "code" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("user_id") REFERENCES "users" ("id")
);

CREATE TABLE classes (
  "id" SERIAL PRIMARY KEY,
  "class_name" text,
  "teacher_id" int NOT NULL,
  "code" text NOT NULL,
  "year" int,
  "description" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("teacher_id") REFERENCES "teachers" ("id")
);

CREATE TABLE teams (
  "id" SERIAL PRIMARY KEY,
  "name" text,
  "class_id" int NOT NULL,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("class_id") REFERENCES "classes" ("id")
);

CREATE TABLE teamMembers (
  "id" SERIAL PRIMARY KEY,
  "team_id" int NOT NULL,
  "student_id" int NOT NULL,
  "role" int NOT NULL,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("team_id") REFERENCES "teams" ("id"),
  FOREIGN KEY ("student_id") REFERENCES "students" ("id")
);

CREATE TABLE projects (
  "id" SERIAL PRIMARY KEY,
  "team_id" int NOT NULL,
  "title" text,
  "content" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("team_id") REFERENCES "teams" ("id")
);

CREATE TABLE project_approval (
  "id" SERIAL PRIMARY KEY,
  "project_id" int NOT NULL,
  "status" boolean,
  "comments" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("project_id") REFERENCES "projects" ("id")
);

CREATE TABLE tasks (
  "id" SERIAL PRIMARY KEY,
  "project_id" int NOT NULL,
  "task_name" text,
  "description" text,
  "status" int,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("project_id") REFERENCES "projects" ("id")
);

CREATE TABLE task_assignments (
  "id" SERIAL PRIMARY KEY,
  "task_id" int NOT NULL,
  "deadline" timestamp,
  "assigned_to" int,
  "assigned_by" int,
  "assignment_time" timestamp,
  "is_transferred" boolean,
  "submission_time" timestamp,
  "is_late_submission" boolean DEFAULT false,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("task_id") REFERENCES "tasks" ("id"),
  FOREIGN KEY ("assigned_to") REFERENCES "users" ("id"),
  FOREIGN KEY ("assigned_by") REFERENCES "users" ("id")
);

CREATE TABLE team_evaluation (
  "id" SERIAL PRIMARY KEY,
  "team_id" int NOT NULL,
  "score" float,
  "comments" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("team_id") REFERENCES "teams" ("id")
);

CREATE TABLE team_member_feedback (
  "id" SERIAL PRIMARY KEY,
  "evaluator_id" int NOT NULL,
  "evaluatee_id" int NOT NULL,
  "team_id" int NOT NULL,
  "feedback" text,
  "rating" int,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("evaluator_id") REFERENCES "users" ("id"),
  FOREIGN KEY ("evaluatee_id") REFERENCES "users" ("id"),
  FOREIGN KEY ("team_id") REFERENCES "teams" ("id")
);

CREATE TABLE appointments (
  "id" SERIAL PRIMARY KEY,
  "participant_id" int NOT NULL,
  "meeting_type" int,
  "date_time" timestamp,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("participant_id") REFERENCES "users" ("id")
);

CREATE TABLE notifications (
  "id" SERIAL PRIMARY KEY,
  "class_id" int NOT NULL,
  "user_id" int NOT NULL,
  "is_seen" boolean DEFAULT false,
  "content" text,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("class_id") REFERENCES "classes" ("id"),
  FOREIGN KEY ("user_id") REFERENCES "users" ("id")
);

CREATE TABLE messages (
  "id" SERIAL PRIMARY KEY,
  "content" text,
  "image" text,
  "is_seen" boolean DEFAULT false,
  "sender_id" int,
  "receiver_id" int,
  "insert_time" timestamp,
  "insert_by" int,
  "update_time" timestamp,
  "update_by" int,
  "delete_time" timestamp,
  "delete_by" int,
  FOREIGN KEY ("sender_id") REFERENCES "users" ("id"),
  FOREIGN KEY ("receiver_id") REFERENCES "users" ("id")
);
