alter table bookmarks
add column note text;

create or replace function getCountOfVoteQuestion(tag_id bigint) returns bigint as $$
select count(vq.user_id) from votes_on_questions vq where user_id = tag_id
$$ language sql;

create or replace function getCountOfVoteAnswer(tag_id bigint) returns bigint as $$
select count(va.user_id) from votes_on_answers va where user_id = tag_id
$$ language sql;


create or replace function getAnswerQuestionVoteCount(tag_id bigint) returns bigint as $$
select getCountOfVoteQuestion(tag_id) + getCountOfVoteAnswer(tag_id)
$$ language sql;