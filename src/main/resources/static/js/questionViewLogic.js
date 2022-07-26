function questionViewLogic(request) {
    fetch('http://localhost:8091/api/user/question/{id}/view', {
        method: 'POST',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(request)
    })
}