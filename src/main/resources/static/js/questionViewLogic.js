
let id = url.substring(url.lastIndexOf('/') + 1).replace(/\D/g, "")


async function setQuestionsInfo(info) {
    questionName.innerHTML = info.title;
    questionText.innerHTML = info.description;
    dateCreated.innerHTML = info.persistDateTime.replace("T", " в ").slice(0, -7);
    dateModified.innerHTML = info.lastUpdateDateTime.replace("T", " в ").slice(0, -7);
    viewedCounter.innerHTML = info.viewCount;
    votes.innerHTML = info.countValuable;
    answerCounter.innerHTML = info.countAnswer;
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
    if (info.isUserVote === 'UP_VOTE') {
        voteUp.disabled = true;
        voteDown.disabled = true;
        voteUpFigure.setAttribute("style", "fill:#17a2b8");
    } else if (info.isUserVote === 'DOWN_VOTE') {
        voteUp.disabled = true;
        voteDown.disabled = true;
        voteDownFigure.setAttribute("style", "fill:#17a2b8");
    }
    if (info.isUserBookmark) {
        bookmarkFigure.setAttribute("style", "fill:#17a2b8");
        addBookmark.disabled = true;
    }
}


console.log("11111111111111")
async function questionViewLogic() {
    let info = {};
    console.log("22222222222222")
    await fetch(`http://localhost:8091/api/user/question/${id}/view`, {
        method: 'POST',
        headers: {
            "Content-type": "application/json",
            'Authorization': 'Bearer ' + token[1]
        }
    })
        .then(data => data.json())
        .then(ob => {info = ob})
        .catch(mess => {
            console.log(mess);
        })
    return info;
}

async function showQuestionInfoOnPage() {
    console.log("33333333333333")
    let questionInfoToShow = await questionViewLogic();
    await setQuestionsInfo(questionInfoToShow);
}

showQuestionInfoOnPage()