
async function questionViewLogic() {
    await fetch(`http://localhost:8091/api/user/question/${questionId}/view`, {
        method: 'POST',
        headers: {
            "Content-type": "application/json",
            'Authorization': 'Bearer ' + token[1]
        }
    })
}