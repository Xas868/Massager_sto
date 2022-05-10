const questionName = document.querySelector('#questionName');
const questionText = document.querySelector('#questionText');
const dateCreated = document.querySelector('#dateCreated');
const dateModified = document.querySelector('#dateModified');
const viewedCounter = document.querySelector('#viewedCounter');
const votes = document.querySelector('#votesCounter');
const answerCounter = document.querySelector('#answerCounter');
const userCardEdited = document.querySelector('#userCard0')
const userCardAsked = document.querySelector('#userCard1')
const questionTags = document.querySelector('.questionTags');
const questionTagsBottom = document.querySelector('.questionTagsBottom');
const voteUp = document.getElementById('voteUp');
const voteDown = document.getElementById('voteDown');
const addBookmark = document.getElementById('bookmark');
const bookmarkCount = document.getElementById('bookmarkCount');
let answersOutput = '';
const answersList = document.querySelector('#answers');
let url = window.location.href;
let questionId = url.substring(url.lastIndexOf('/') + 1).replace(/\D/g, "");
const questionComments = document.querySelector('#questionComments');
const questionCommentButton = document.getElementById('questionCommentButton');
let commentsOutput = '';
let commentAnswerOutput = '';
const addAnswerButton = document.getElementById('addAnswerButton');
let answerId = '';

//-----Блок вопроса-----------------------------------------------------------------------------------------------

async function makeUpVoteForQuestion() {
    await fetch(`http://localhost:8091/api/user/question/${questionId}/upVote`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

async function fetchQuestionInfo() {
    let info = {};
    await fetch(`/api/user/question/${questionId}`, {
            method: 'GET',
            headers: {
                "Content-type": "application/json",
                "Authorization": 'Bearer ' + token[1],
            }
        }
    )
        .then(data => data.json())
        .then(ob => {info = ob})
        .catch(mess => {
            console.log(mess);
        })
    return info;
}

async function showQuestionInfoOnPage() {
    let questionInfoToShow = await fetchQuestionInfo();
    await setQuestionInfo(questionInfoToShow);
}

showQuestionInfoOnPage();

async function setQuestionInfo(info) {
    questionName.innerHTML = info.title;
    questionText.innerHTML = info.description;
    dateCreated.innerHTML = info.persistDateTime.replace("T", " в ").slice(0, -7);
    dateModified.innerHTML = info.lastUpdateDateTime.replace("T", " в ").slice(0, -7);
    viewedCounter.innerHTML = info.viewCount;
    votes.innerHTML = info.countValuable;
    answerCounter.innerHTML = info.countAnswer;
    userCardEdited.innerHTML =
        '<div>' +
            '<div>' +
                '<a id="lastChangeLink" href="#">' +
                    '<small>редактирован <span>' + info.lastUpdateDateTime.replace("T", " в ").slice(0, -10) + '</span></small>' +
                '</a>' +
            '</div>' +
            '<div class="pr-2" style="display: inline-block">' +
                '<img src="/images/noUserAvatar.png" style="width: 50px; height: 50px" alt="...">' +
            '</div>' +
            '<div class="align-items-top" style="display: inline-block; vertical-align: bottom">' +
                '<div class="align-items-top"><a class="align-top" href="#">Редактор</a></div>' +
                '<div class="text-muted">Репутация</div>' +
            '</div>' +
        '</div>';
    userCardAsked.innerHTML =
        '<div>' +
            '<div>' +
                '<small class="text-muted">' +
                    'задан <span>' + info.persistDateTime.replace("T", " в ").slice(0, -10) + '</span>' +
                '</small>' +
            '</div>' +
            '<div class="pr-2" style="display: inline-block">' +
                '<img src="/images/noUserAvatar.png" style="width: 50px; height: 50px" alt="...">' +
            '</div>' +
            '<div class="align-items-top" style="display: inline-block; vertical-align: bottom">' +
                '<div class="align-items-top"><a class="align-top" href="#">' + info.authorName + '</a></div>' +
                '<div class="text-muted">'+ info.authorReputation + '</div>' +
            '</div>' +
        '</div>';
    let questionTagsHtml = "";
    info.listTagDto.forEach(tag => questionTagsHtml += '<a href="#"><span class="badge bg-info text-light mr-1 mb-1 mt-1">' + tag.name + '</span></a>');
    questionTags.innerHTML = questionTagsHtml;
    questionTagsBottom.innerHTML = questionTagsHtml;
}

voteUp.onclick = async function() {
    await makeUpVoteForQuestion();
    fetchQuestionInfo().then(res => {votes.innerHTML = res.countValuable});
}

async function makeDownVoteForQuestion(){
    await fetch(`http://localhost:8091/api/user/question/${questionId}/downVote`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

voteDown.onclick = async function() {
    await makeDownVoteForQuestion();
    fetchQuestionInfo().then(res => {votes.innerHTML = res.countValuable});
}

async function addBookmarkForQuestion(){
    await fetch(`http://localhost:8091/api/user/question/${questionId}/bookmark`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

addBookmark.onclick = async function() {
    await addBookmarkForQuestion();
    bookmarkCount.innerHTML = "В базу добавлено, но в api нет возможности получить число всех закладок вопроса"
    addBookmark.disabled = true;
}

//-----Комментарии к вопросам-----

questionCommentButton.onclick = async function() {
    let commentTextToAdd = document.getElementById('inputCommentField').value;
    document.getElementById('inputCommentField').value = "";
    fetch(`/${questionId}/comment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        },
        body: commentTextToAdd
    })
        .then(response => response.json())
        .then(data => {
            const newComment = [];
            newComment.push(data);
            showQuestionCommentsOnPage(newComment);
        });
}

function showQuestionCommentsOnPage(data) {
    for (let questionComment in data) {
        commentsOutput += `
        <li class="list-group-item border-right-0 border-left-0 px-0">
            <div class="row mx-0">
                <div class="col-1 d-flex justify-content-center">
                    <span class="usefulCommentVotes">0</span>
                </div>
                <div class="col">
                    <span class="comment-text">${data[questionComment].text}<span> – </span><a href="#" class="comment-user">User ${data[questionComment].userId}</a>
                    <span class="comment-data" style="color:#9199a1">${data[questionComment].persistDate.replace("T", " в ").slice(0, -7)}</span>
                </div>
            </div>
        </li>`;
    }
    questionComments.innerHTML = commentsOutput;
}

fetch(`/api/user/question/${questionId}/comment`, {
        method: 'GET',
        headers: {
            "Content-type": "application/json",
            "Authorization": 'Bearer ' + token[1],
        }
    }
)
    .then(response => response.json())
    .then(data => showQuestionCommentsOnPage(data))
    .catch(error => console.log(error));

//-----Блок ответов-------------------------------------------------------------------------------------------------

addAnswerButton.onclick = async function() {
    let answerTextToAdd = document.getElementById('inputAnswerField').value;
    document.getElementById('inputAnswerField').value = "";
    await fetch(`/api/user/question/${questionId}/answer/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        },
        body: answerTextToAdd
    });
    await fetch(`/api/user/question/${questionId}/answer`, {
            method: 'GET',
            headers: {
                "Content-type": "application/json",
                "Authorization": 'Bearer ' + token[1],
            }
        }
    )
        .then(response => response.json())
        .then(data => showAnswersOnPage(data))
        .catch(error => console.log(error));
    let questionInfoForCount = await fetchQuestionInfo();
    answerCounter.innerHTML = questionInfoForCount.countAnswer;
}

const showAnswersOnPage = (answersData) => {
    answersOutput = ``;
    answersData.forEach(answer => {
        answersOutput += `
        <div class="row">
            <div class="col-1 d-flex justify-content-center align-items-start">
                <div class="d-flex flex-column justify-content-center">
                    <button id="voteUpAnswer" class="p-0 btn btn_link_custom voteUpAnswer" data-toggle="tooltip"
                            data-placement="right" title="Полезный вопрос">
                        <svg aria-hidden="true" class="svg-icon iconArrowUpLg" width="36" height="36"
                             viewBox="0 0 36 36">
                            <path d="M2 25h32L18 9 2 25Z" id="voteUpAnswer${answer.id}"></path>
                        </svg>
                    </button>

                    <div id="votesCounterAnswer${answer.id}" class="d-flex justify-content-center">
                        <span id="votesAnswer${answer.id}">${answer.countValuable}</span>
                    </div>

                    <button id="voteDownAnswer" class="p-0 btn btn_link_custom voteDownAnswer" data-toggle="tooltip"
                            data-placement="right" title="Бесполезный вопрос">
                        <svg aria-hidden="true" class="svg-icon iconArrowDownLg" width="36" height="36"
                             viewBox="0 0 36 36">
                            <path d="M2 11h32L18 27 2 11Z" id="voteDownAnswer${answer.id}></path>
                        </svg>
                    </button>

                    <a id="postUpdatesAnswer" class="d-flex justify-content-center" href="#" data-toggle="tooltip"
                       data-placement="right" title="Изменения, связанные с вопросом">
                        <svg aria-hidden="true" class="mln2 mr0 svg-icon iconHistory" width="19" height="18"
                             viewBox="0 0 19 18">
                            <path d="M3 9a8 8 0 1 1 3.73 6.77L8.2 14.3A6 6 0 1 0 5 9l3.01-.01-4 4-4-4h3L3 9Zm7-4h1.01L11 9.36l3.22 2.1-.6.93L10 10V5Z"></path>
                        </svg>
                    </a>
                </div>
            </div>

            <div class="col">
                <div class="row d-flex flex-column">
                    <div id="answerText">
                        <p>${answer.htmlBody}</p>
                    </div>

                    <div class="row mx-0 bottomBlock d-flex justify-content-between">
                        <div>
                            <a type="button" id="dropdownShareButtonAnswer" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color: #6a737c;">Поделиться</a>
                            <div class="dropdown-menu" aria-labelledby="dropdownShareButton">
                                <span class="px-1">Поделись ссылкой на этот ответ:</span>
                                <input class="mx-1" style="width:90%" placeholder="<ссылка на ответ>">
                            </div>
                            <a type="button" style="color: #6a737c;">Изменить</a>
                            <a type="button" style="color: #6a737c;">Следить</a>
                        </div>
                        <div class="d-flex flex-row">
                            <div class="userCard">
                                <div class="container">
                                    <div class="d-flex flex-column flex-wrap">
                                        <div id="editorCard${answer.id}" class="row">
                                            <div>
                                                <div>
                                                    <a id="lastChangeLink" href="#">
                                                        <small>редактирован <span>${answer.persistDateTime.replace("T", " в ").slice(0, -10)}</span></small>
                                                    </a>
                                                </div>
                                                <div class="pr-2" style="display: inline-block">
                                                    <img src="/images/noUserAvatar.png" style="width: 50px; height: 50px" alt="...">
                                                </div>
                                                <div class="align-items-top" style="display: inline-block; vertical-align: bottom">
                                                    <div class="align-items-top"><a class="align-top" href="#">Редактор</a></div>
                                                    <div class="text-muted">Репутация</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="userCard">
                                <div class="container">
                                    <div class="d-flex flex-column">
                                        <div id="userCard${answer.userId}" class="row">
                                            <div>
                                                <div>
                                                    <small class="text-muted">задан <span>${answer.persistDateTime.replace("T", " в ").slice(0, -10)}</span></small>
                                                </div>
                                                <div class="pr-2" style="display: inline-block">
                                                    <img src="/images/noUserAvatar.png" style="width: 50px; height: 50px" alt="...">
                                                </div>
                                                <div class="align-items-top" style="display: inline-block; vertical-align: bottom">
                                                    <div class="align-items-top"><a class="align-top" href="#">User ${answer.userId}</a></div>
                                                    <div class="text-muted">${answer.userReputation}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row d-flex flex-column py-2">
                    <div id="commentAnswerList${answer.id}">
                        <ul class="list-group mb-2">
                            <!--ЭЛЕМЕНТЫ НИЖЕ ЯВЛЯЮТСЯ ДЕМОНСТРАТИВНЫМИ ШАБЛОНАМИ-->
                            <li class="list-group-item border-right-0 border-left-0 px-0">
                                <div class="row mx-0">
                                    <div class="col-1 d-flex justify-content-center">
                                        <span class="usefulCommentVotesCounter">123</span>
                                    </div>
                                    <div class="col">
                                <span class="comment-text" style="color: red">В api нет возможности получить список комментариев к ответу, комментарий добавится в базу, но не будет отображен</span>
                                        –&nbsp;<a href="#" class="comment-user">Имя Пользователя 1</a>
                                        <span class="comment-data"
                                              style="color:#9199a1">15 сентября 2021, 17:49</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <textarea class="col px-1" id="inputCommentFieldAnswer${answer.id}" rows="3"
                          style="resize:none"></textarea>
                        <button class="btn btn-info mt-2 addCommentAnswerButton" id="addCommentAnswerButton${answer.id}">Добавить комментарий</button>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        `;
    });
    answersList.innerHTML = answersOutput;
}

fetch(`/api/user/question/${questionId}/answer`, {
        method: 'GET',
        headers: {
            "Content-type": "application/json",
            "Authorization": 'Bearer ' + token[1],
        }
    }
)
    .then(response => response.json())
    .then(data => showAnswersOnPage(data))
    .catch(error => console.log(error));

async function fetchAnswerInfo() {
    let answerInfo = {};
    await fetch(`/api/user/question/${questionId}/answer`, {
            method: 'GET',
            headers: {
                "Content-type": "application/json",
                "Authorization": 'Bearer ' + token[1],
            }
        }
    )
        .then(data => data.json())
        .then(ob => {answerInfo = ob})
        .catch(mess => {
            console.log(mess);
        })
    return answerInfo;
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

async function filterById(jsonObject, id) {
    return jsonObject.filter(function(jsonObject) {return (jsonObject['id'] == id);})[0];
}

on(document, 'click', '.voteUpAnswer', async e => {
    let voteUpAnswerId = e.target.id;
    voteUpAnswerId = voteUpAnswerId.replace(/\D/g, "");
    await fetch(`/api/user/question/${questionId}/answer/${voteUpAnswerId}/upVote`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
    let answers = await fetchAnswerInfo();
    let countAnswerVotes = await filterById(answers, voteUpAnswerId);
    let votesAnswer = document.getElementById('votesAnswer' + voteUpAnswerId);
    votesAnswer.innerHTML = countAnswerVotes.countValuable;
})

on(document, 'click', '.voteDownAnswer', async e => {
    let voteDownAnswerId = e.target.id;
    voteDownAnswerId = voteDownAnswerId.replace(/\D/g, "");
    await fetch(`/api/user/question/${questionId}/answer/${voteDownAnswerId}/downVote`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
    let answers = await fetchAnswerInfo();
    let countAnswerVotes = await filterById(answers, voteDownAnswerId);
    let votesAnswer = document.getElementById('votesAnswer' + voteDownAnswerId);
    votesAnswer.innerHTML = countAnswerVotes.countValuable;
})

//-----Комментарии к ответам-----

on(document, 'click', '.addCommentAnswerButton', async e => {
    let commentAnswerId = e.target.id;
    commentAnswerId = commentAnswerId.replace(/\D/g, "");
    let commentAnswerTextToAdd = document.getElementById(`inputCommentFieldAnswer${commentAnswerId}`).value;
    document.getElementById(`inputCommentFieldAnswer${commentAnswerId}`).value = '';
    await fetch(`/api/user/question/${questionId}/answer/${commentAnswerId}/comment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        },
        body: commentAnswerTextToAdd
    });
});

// Использовать как появится api
function showAnswerCommentsOnPage(data) {
    data.forEach(answerComment => {
        commentAnswerOutput += `
        <li class="list-group-item border-right-0 border-left-0 px-0">
            <div class="row mx-0">
                <div class="col-1 d-flex justify-content-center">
                    <span class="usefulCommentVotes">23</span>
                </div>
                <div class="col">
                    <span class="comment-text">текст!</span><a href="#" class="comment-user">Имя Пользователя 3</a>
                    <span class="comment-data" style="color:#9199a1">17 сентября 2021, 17:29</span>
                </div>
            </div>
        </li>
        `;
    });
}
