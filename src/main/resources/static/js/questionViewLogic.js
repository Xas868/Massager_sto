
let id = url.substring(url.lastIndexOf('/') + 1).replace(/\D/g, "")

async function questionViewLogic() {
    let info = {};
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
    let questionInfoToShow = await questionViewLogic();
    await setQuestionInfo(questionInfoToShow);
}

showQuestionInfoOnPage()