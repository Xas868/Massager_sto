activateSideBar()

function activateSideBar () {
    document.querySelector("#sidebar_questions").classList.add("active")
}

function getCookie(name){
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? ('Bearer ' + decodeURIComponent(matches[1])) : undefined;
}

async function getRelatedTags(numItems){

    let tags = {};
    let token1 = getCookie('token');
    let relatedTags = document.querySelector(`#relatedTags`);
    relatedTags.innerHTML = '';

    await fetch('/api/user/tag/related', {
        method: 'GET',
        headers: {
            "Content-type": "application/json",
            "Authorization": `${token1}`,
        }
    })
        .then(data => data.json())
        .then(ob => {tags = ob})
        .catch(mess => {
            console.log(mess);
        })

    if(numItems === 0){
        numItems = tags.length;
    }

    for (let num = 0; num < numItems; num++){

        let divFirst = document.createElement('div');
        divFirst.classList.add("mb-1");

        divFirst.innerHTML =
            '<a href="#"><span class="badge bg-info text-light">'+tags[num].name+'</span></a>'+
            '<span class="text-muted">×</span>'+
            '<span class="text-muted">'+tags[num].countQuestion+'</span>';
        relatedTags.appendChild(divFirst);
    }

    let divLink = document.createElement('div');
    divLink.classList.add("btn_link_custom");
    divLink.classList.add("mt-2");
    divLink.innerHTML = "больше связанных меток";

    relatedTags.appendChild(divLink);

    divLink.addEventListener("click", {
        handleEvent(event){
            getRelatedTags(0);
        }
    });
}

getRelatedTags(3);

let selectedValue;
let divTrackedList;
let divIgnoredList;
let listTrackedTag;
let listIgnoredTag;
let iptAddTrackedTag;
let iptAddIgnoredTag;
let btnAddTrackedTag;
let btnAddIgnoredTag;
let btnMakeTrackedCard = document.querySelector("#btn_add_tag_tracked");
let btnMakeIgnoredCard = document.querySelector("#btn_add_tag_ignored");

function makeTrackedCard(){
    btnMakeTrackedCard.parentElement.innerHTML =
        '<div class="mb-1" id="trackedList"></div>' +
        '<div>' +
            '<span class="d-inline-block">' +
                '<input class="form-control" list="listTrackedTag" type="text" id="ipt_add_tag_tracked">' +
                '<datalist id="listTrackedTag"></datalist>' +
            '</span>' +
            '<span class="d-inline-block">' +
        '       <a class="btn btn-info mb-1" id="btn_add_tracked_tag" href="#">+</a>' +
            '</span>' +
        '</div>';

    listTrackedTag = document.querySelector("#listTrackedTag");
    iptAddTrackedTag = document.querySelector("#ipt_add_tag_tracked");
    iptAddTrackedTag.addEventListener('input', () => {

        tagInputEvent(iptAddTrackedTag.value, listTrackedTag, iptAddTrackedTag);
    })

    iptAddTrackedTag.addEventListener('keypress', () => {
        if (event.charCode === 13){
            addTagClickEvent(addTag, addTagInCard, divTrackedList, iptAddTrackedTag, "tracked");
        }
    })

    divTrackedList = document.querySelector("#trackedList");
    btnAddTrackedTag = document.querySelector("#btn_add_tracked_tag");
    btnAddTrackedTag.addEventListener('click', () => {
        addTagClickEvent(addTag, addTagInCard, divTrackedList, iptAddTrackedTag, "tracked")
            .then(() => createPagination())
            .then(() => generateFilter())
            .then(res => questionPagination.filter = res)
            .then(()=>console.log(questionPagination.filter))
            .then(() => init());
    })
}

async function generateFilter() {
    let trackingAndIgnoredFilter = '';
    let trackedTags = await getTags('tracked');
    for (let i = 0; i < trackedTags.length; i++) {
        trackingAndIgnoredFilter += '&trackedTag=' + trackedTags[i].id;
    }
    let ignoredTags = await getTags('ignored');
    for (let i = 0; i < ignoredTags.length; i++) {
        trackingAndIgnoredFilter += '&ignoredTag=' + trackedTags[i].id;
    }
    return trackingAndIgnoredFilter;
}

function makeIgnoredCard(){
    btnMakeIgnoredCard.parentElement.innerHTML =
        '<div class="mb-1" id="ignoredList"> </div>' +
        '<div>' +
            '<div class="d-inline-block">' +
                '<input class="form-control" list="listIgnoredTag" type="text" id="ipt_add_tag_ignored">' +
                '<datalist id="listIgnoredTag"></datalist>' +
            '</div>' +
            '<div class="d-inline-block">' +
                '<a class="btn btn-info mb-1" id="btn_add_ignored_tag" href="#">+</a>' +
            '</div>' +
        '</div>';

    listIgnoredTag = document.querySelector("#listIgnoredTag");
    iptAddIgnoredTag = document.querySelector("#ipt_add_tag_ignored");
    iptAddIgnoredTag.addEventListener('input', () => {

        tagInputEvent(iptAddIgnoredTag.value, listIgnoredTag, iptAddIgnoredTag);
    })

    iptAddIgnoredTag.addEventListener('keypress', () => {
        if (event.charCode === 13){
            addTagClickEvent(addTag, addTagInCard, divIgnoredList, iptAddIgnoredTag, "ignored");
        }
    })

    divIgnoredList = document.querySelector("#ignoredList");
    btnAddIgnoredTag = document.querySelector("#btn_add_ignored_tag");
    btnAddIgnoredTag.addEventListener('click', () => {

        addTagClickEvent(addTag, addTagInCard, divIgnoredList, iptAddIgnoredTag, "ignored");
    })
}

async function loadCards(){

    let tags = await getTags("tracked");

    if(tags.length === 0
        || tags.length === undefined){
        return
    }

    await makeTrackedCard();

    for (let num = 0; num < tags.length; num++){
        addTagInCard(tags[num], divTrackedList, "tracked");
    }

    tags = await getTags("ignored");

    if(tags.length === 0
        || tags.length === undefined){
        return
    }

    await makeIgnoredCard();

    for (let num = 0; num < tags.length; num++){
        addTagInCard(tags[num], divIgnoredList, "ignored");
    }
}

loadCards();

btnMakeTrackedCard.addEventListener('click', () => {
    makeTrackedCard();
})

btnMakeIgnoredCard.addEventListener('click', () => {
    makeIgnoredCard();
})

function tagInputKeyPressEvent(){

}

async function addTagClickEvent(addTags, addTagInCard, divList, iptAddTag, mode){

    await addTag(selectedValue, mode)
            .then(
                tags => {addTagInCard(tags, divList, mode)}
            );
    iptAddTag.value = "";
}

async function tagInputEvent(value, tagsList, iptAddTag){

    tagsList.innerHTML = "";

    await getTagsListLike(value)
            .then(
                tags => {fillDataList(tags, tagsList)}
            );

    if(iptAddTag.list.options.length === 1){
        selectedValue = [...iptAddTag.list.options].find(option => option.innerText === iptAddTag.value).id;
        tagsList.innerHTML = "";
    }else{
        selectedValue = undefined;
    }
}

async function tagClickEvent(tag, mode){

    await deleteTag(tag, mode);
    tag.remove();
}

function addTagInCard(tag, divList, mode){

    if(tag.name === undefined){return;}

    let eventClick = "tagClickEvent(this, '" + mode + "')";

    let newDivTag = document.createElement('div');
    newDivTag.classList.add("mt-1");
    newDivTag.classList.add("mb-1");
    newDivTag.innerHTML = tag.name;

    let newTag = document.createElement('span');
    newTag.classList.add("mr-1");
    newTag.classList.add("badge");
    newTag.classList.add("bg-info");
    newTag.classList.add("text-light");
    newTag.setAttribute('id', tag.id);
    newTag.setAttribute('onclick', eventClick);
    newTag.appendChild(newDivTag);

    divList.appendChild(newTag);
}

function fillDataList(tags, tagsList){

    if(tags.length === 0){return;}

    for (let num = 0; num < tags.length; num++){

        let option = document.createElement('option');
        option.text = tags[num].name;
        option.id   = tags[num].id;
        tagsList.appendChild(option);
    }
}

async function getTagsListLike(value){
    return myFetch('/api/user/tag/latter?value='+value, 'GET');
}

async function getTags(mode){
    return myFetch('/api/user/tag/'+mode, 'GET');
}

async function addTag(id, mode){
    return myFetch('/api/user/tag/'+mode+'/add?tag='+id, 'POST');
}

async function deleteTag(tag, mode){
    return myFetch('/api/user/tag/'+mode+'/delete?tag='+tag.id, 'DELETE');
}

async function myFetch(URL, httpMethod){

    let tags = {};
    let token1 = getCookie('token');

    await fetch(URL, {
            method: httpMethod,
            headers: {
                "Content-type": "application/json",
                "Authorization": `${token1}`,
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
//----------------------------------------------------
//Работа пагинации с учётом отслеживаемых и игнорируемых тегов
// Функция заполнения вопроса тегами

function showTagsForQuestion(tags, questionTagsList) {
    let output = '';
    tags.forEach(tag => {
        output += '<a href="#"><span class="badge bg-info text-light mr-1 mb-1">' + tag.name + '</span>';
    });
    questionTagsList.innerHTML = output;
}

// Функция получения тегов вопроса
async function fetchQuestionTags(URL){
    let questionTags = {};
    let token1 = getCookie('token');
    await fetch(URL, {
            method: 'GET',
            headers: {
                "Content-type": "application/json",
                "Authorization": `${token1}`,
            }
        }
    )
        .then(data => data.json())
        .then(ob => {questionTags = ob})
        .catch(mess => {
            console.log(mess);
        })
    return questionTags;
}

//создаем новый объект пагинации и передаем аргументы в конструктор
let questionPagination;
function createPagination() {
    questionPagination = new Pagination(
        '/api/user/question',
        3,
        'pagination_objects',
        'navigation',
        function (arrayObjects) {
            let divFirst = document.createElement('div');
            if (arrayObjects != null && arrayObjects.length > 0) {
                for (let num = 0; num < arrayObjects.length; num++) {
                    let divCard = document.createElement('div');
                    divCard.classList.add("card");
                    divCard.classList.add("mb-3");
                    let questionId = arrayObjects[num].id;
                    let questionTitle = arrayObjects[num].title;
                    let questionDescription = arrayObjects[num].description;
                    let formId = 'question-tags' + questionId;
                    let questionTagsList = document.createElement('div');
                    questionTagsList.setAttribute('id', formId);
                    fetchQuestionTags('http://localhost:8091/api/user/question?page=1')
                        .then(result => result.items[questionId - 1].listTagDto)
                        .then(tags => showTagsForQuestion(tags, questionTagsList));
                    divCard.innerHTML = '';
                    divCard.innerHTML +=
                        '<div class="card-body p-1">' +
                            '<div class="row">' +
                                '<div class="col-2 align-items-center">' +
                                    '<div class="text-center">' +
                                        '<div>100</div>' +
                                        '<small>голосов</small>' +
                                    '</div>' +
                                    '<div class="text-center text-info border border-info rounded">' +
                                        '<div>200</div>' +
                                        '<small>ответов</small>' +
                                    '</div>' +
                                    '<div class="text-center">' +
                                        '<div>300</div>' +
                                        '<small>показов</small>' +
                                    '</div>' +
                                '</div>' +
                                '<div class="col-10">' +
                                    '<a class="mb-1" href="#">' + questionTitle + '</a>' +
                                    '<p class="mb-1">' + questionDescription + '</p>' +
                                    '<div class="row">' +
                                        '<div class="col-7">' +
                                            // children[0].children[0].children[1].children[2].children[0] - вставка questionTagsList
                                        '</div>' +
                                        '<div class="col-5">' +
                                            '<div>' +
                                                '<div>' +
                                                    '<small class="text-muted">' +
                                                        'задан <span>26 дек \'21 в 00:00</span>' +
                                                    '</small>' +
                                                '</div>' +
                                                '<div style="display: inline-block">' +
                                                    '<img src="/images/noUserAvatar.png"' +
                                                         ' style="width: 50px; height: 50px" alt="...">' +
                                                '</div>' +
                                                '<div class="align-items-top"' +
                                                     ' style="display: inline-block; vertical-align: bottom">' +
                                                    '<div class="align-items-top">' +
                                                        '<a class="align-top" href="#">Username1</a>' +
                                                    '</div>' +
                                                    '<div class="text-muted">250</div>' +
                                                '</div>' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>' +
                                '</div>' +
                            '</div>' +
                        '</div>';
                    divCard.children[0].children[0].children[1].children[2].children[0].appendChild(questionTagsList);
                    divFirst.appendChild(divCard);
                }
            }
            return divFirst;
        }
    );
}

createPagination();
init();
function showPage(event, num) {
    questionPagination.showPage(event, num);
}
async function init() {
    await questionPagination.showPage(null, 1);
}













