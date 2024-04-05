CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email TEXT UNIQUE NOT NULL,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  role INT NOT NULL,
  name TEXT,
  phone TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT
);

CREATE TABLE teachers (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE students (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  code TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE classes (
  id SERIAL PRIMARY KEY,
  class_name TEXT,
  teacher_id INT NOT NULL,
  code TEXT NOT NULL,
  year INT,
  description TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (teacher_id) REFERENCES teachers (id)
);

CREATE TABLE teams (
  id SERIAL PRIMARY KEY,
  name TEXT,
  class_id INT NOT NULL,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (class_id) REFERENCES classes (id)
);

CREATE TABLE teamMembers (
  id SERIAL PRIMARY KEY,
  team_id INT NOT NULL,
  student_id INT NOT NULL,
  role INT NOT NULL,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (team_id) REFERENCES teams (id),
  FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE projects (
  id SERIAL PRIMARY KEY,
  team_id INT NOT NULL,
  title TEXT,
  content TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE planning (
  id SERIAL PRIMARY KEY,
  project_id INT NOT NULL,
  insert_time TIMESTAMP,
  insert_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  update_time TIMESTAMP,
  update_by INT,
  FOREIGN KEY (project_id) REFERENCES projects (id)
);

CREATE TABLE sprints (
  id SERIAL PRIMARY KEY,
  plan_id INT NOT NULL,
  name TEXT,
  stage INT,
  insert_time TIMESTAMP,
  insert_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  update_time TIMESTAMP,
  update_by INT,
  FOREIGN KEY (plan_id) REFERENCES planning (id)
);

CREATE TABLE project_approval (
  id SERIAL PRIMARY KEY,
  project_id INT NOT NULL,
  status BOOLEAN,
  comments TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (project_id) REFERENCES projects (id)
);

CREATE TABLE tasks (
  id SERIAL PRIMARY KEY,
  sprint_id INT NOT NULL,
  task_name TEXT,
  description TEXT,
  status INT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (sprint_id) REFERENCES sprints (id)
);

CREATE TABLE task_assignments (
  id SERIAL PRIMARY KEY,
  task_id INT NOT NULL,
  deadline TIMESTAMP,
  assigned_to INT,
  assigned_by INT,
  assignment_time TIMESTAMP,
  is_transferred BOOLEAN,
  submission_time TIMESTAMP,
  is_late_submission BOOLEAN DEFAULT FALSE,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (task_id) REFERENCES tasks (id),
  FOREIGN KEY (assigned_to) REFERENCES users (id),
  FOREIGN KEY (assigned_by) REFERENCES users (id)
);

CREATE TABLE team_evaluation (
  id SERIAL PRIMARY KEY,
  team_id INT NOT NULL,
  score FLOAT,
  comments TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE team_member_feedback (
  id SERIAL PRIMARY KEY,
  evaluator_id INT NOT NULL,
  evaluatee_id INT NOT NULL,
  team_id INT NOT NULL,
  feedback TEXT,
  rating INT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (evaluator_id) REFERENCES users (id),
  FOREIGN KEY (evaluatee_id) REFERENCES users (id),
  FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE appointments (
  id SERIAL PRIMARY KEY,
  participant_id INT NOT NULL,
  meeting_type INT,
  date_time TIMESTAMP,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (participant_id) REFERENCES users (id)
);

CREATE TABLE notifications (
  id SERIAL PRIMARY KEY,
  class_id INT NOT NULL,
  user_id INT NOT NULL,
  is_seen BOOLEAN DEFAULT FALSE,
  content TEXT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (class_id) REFERENCES classes (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE messages (
  id SERIAL PRIMARY KEY,
  content TEXT,
  image TEXT,
  is_seen BOOLEAN DEFAULT FALSE,
  sender_id INT,
  receiver_id INT,
  insert_time TIMESTAMP,
  insert_by INT,
  update_time TIMESTAMP,
  update_by INT,
  delete_time TIMESTAMP,
  delete_by INT,
  FOREIGN KEY (sender_id) REFERENCES users (id),
  FOREIGN KEY (receiver_id) REFERENCES users (id)
);

CREATE TABLE tokens (
  id SERIAL PRIMARY KEY,
  token TEXT,
  user_id INT NOT NULL,
  insert_time TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id)
)
