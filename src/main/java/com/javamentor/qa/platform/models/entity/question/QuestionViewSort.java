package com.javamentor.qa.platform.models.entity.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionViewSort {

    NEW(" from Question q " +
                        " where ((:trackedTag) is null or q.id in (select q.id from Question q1 JOIN q1.tags t where q.id = q1.id and (t.id in (:trackedTag))))" +
                        " and q.id not in  (select q.id from Question q1 JOIN q1.tags t where q.id = q1.id and t.id in (:ignoredTag) )" +
                        " and (:dateFilter = 0  OR q.persistDateTime >= current_date - :dateFilter) " +
                        " ORDER BY q.persistDateTime desc"),

    NoAnswer(" from Question q" +
                             " left JOIN q.tags t " +
                             " WHERE q.answers IS EMPTY AND ((:trackedTag) IS NULL OR t.id IN (:trackedTag)) " +
                             " AND ((:ignoredTag) IS NULL OR q.id not IN (select q.id from Question q join q.tags t where t.id in (:ignoredTag)))  " +
                             " and (:dateFilter = 0 or q.persistDateTime >= current_date - :dateFilter) "),

    VIEW(" from Question q" +
                         " where ((:trackedTag) IS NULL OR q.id IN (select q.id from Question q join q.tags t where t.id in (:trackedTag))) " +
                         " and ((:ignoredTag) IS NULL OR q.id not IN (select q.id from Question q join q.tags t where t.id in (:ignoredTag)))" +
                         " and (:dateFilter = 0 or q.persistDateTime >= current_date - :dateFilter) " +
                         " order by view_count desc");

    private final String comparingField;
}
