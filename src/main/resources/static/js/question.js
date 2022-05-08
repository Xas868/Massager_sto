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








async function fetchQuestionInfo() {
    return myFetch('/api/user/question/1', 'GET');
}

async function myFetch(URL, httpMethod){

    let tags = {};

    await fetch(URL, {
            method: httpMethod,
            headers: {
                "Content-type": "application/json",
                "Authorization": 'Bearer ' + token[1],
            }
        }
    )
        .then(data => data.json())
        .then(ob => {tags = ob})
        .catch(mess => {
            console.log(mess);
        })

    return tags;
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
                '<div class="align-items-top"><a class="align-top" href="#">Автор</a></div>' +
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

async function makeUpVoteForQuestion(){
    await fetch('http://localhost:8091/api/user/question/1/upVote',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

voteUp.onclick = async function () {
    await makeUpVoteForQuestion();
    fetchQuestionInfo().then(res => {votes.innerHTML = res.countValuable});
}

async function makeDownVoteForQuestion(){
    await fetch('http://localhost:8091/api/user/question/1/downVote',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

voteDown.onclick = async function () {
    await makeDownVoteForQuestion();
    fetchQuestionInfo().then(res => {votes.innerHTML = res.countValuable});
}

async function addBookmarkForQuestion(){
    await fetch('http://localhost:8091/api/user/question/1/bookmark',{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token[1]
        }
    })
}

addBookmark.onclick = async function () {
    await addBookmarkForQuestion();
    bookmarkCount.innerHTML = "В базу добавлено, но у нас нет api для подсчёта всех закладок вопроса, после F5 будет снова 0"
    addBookmark.disabled = true;
}
