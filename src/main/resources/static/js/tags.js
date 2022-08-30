const sortedByPopularityUrl = '/api/user/tag/popular';
const sortedByNameUrl = '/api/user/tag/name';
const sortedByDateUrl = '/api/user/tag/new';

activateSideBar()

function activateSideBar () {
    document.querySelector("#sidebar_tags").classList.add("active")
}

let filter = document.getElementById('filterByTag').value;

//создаем новый объект пагинации и передаем аргументы в конструктор
let pagination;
function createPagination(url) {
    pagination = new Pagination(
        url,
        10,
        'pagination_objects',
        'navigation',
        function (arrayObjects) {

            let divFirst = document.createElement('div');
            if (arrayObjects != null && arrayObjects.length > 0) {

                let numCardsInDeck = 0;
                let divCardDeck = undefined;

                for (let num = 0; num < arrayObjects.length; num++) {

                    if(numCardsInDeck === 0) {
                        numCardsInDeck = 2;
                        divCardDeck = document.createElement('div');
                        divCardDeck.classList.add("card-deck");
                        divCardDeck.classList.add("tags-card-deck");
                        divFirst.appendChild(divCardDeck);
                    }

                    let divShell = document.createElement('div');
                    let divCard = document.createElement('div');
                    divShell.classList.add("col-6");
                    divShell.classList.add("p-0");
                    divCard.classList.add("card");

                    countQuestion        = arrayObjects[num].countQuestion;
                    description          = arrayObjects[num].description;
                    nameTag              = arrayObjects[num].name;
                    questionCountOneDay  = arrayObjects[num].questionCountOneDay;
                    questionCountWeekDay = arrayObjects[num].questionCountWeekDay;

                    divCard.innerHTML =
                        '<div class="tag-card">'+
                        '   <div class="card-body">'+
                        '       <div class="card-title"><a href="#"><span class="badge bg-info text-light">'+nameTag+'</span></a></div>'+
                        '       <p class="card-text small">'+description+'</p>'+
                        '       <div class="row">'+
                        '           <div class="col"><span class="card-text small text-muted">'+countQuestion+' questions</span></div>'+
                        '           <div class="col"><a class="card-link small text-muted" href="#">'+questionCountOneDay+' asked today, '+questionCountWeekDay+' this week</a></div>'+
                        '       </div>'+
                        '   </div>'+
                        '</div>';

                    divShell.appendChild(divCard);
                    divCardDeck.appendChild(divShell);
                    numCardsInDeck--;
                }
            }
            return divFirst;
        },
        filter
    );
}

createPagination(sortedByPopularityUrl);
init();
function showPage(event, num) {
    pagination.showPage(event, num);
}
async function init() {
    await pagination.showPage(null, 1);
}

// Работа фильтрации по имени тега
let filterInput = document.getElementById('filterByTag');
filterInput.addEventListener('keyup', filterTags);
function filterTags() {
    filter = document.getElementById('filterByTag').value;
    createPagination();
    init();
    function showPage(event, num) {
        pagination.showPage(event, num);
    }
    async function init() {
        await pagination.showPage(null, 1);
    }
}

let sortByPopularityButton = document.getElementById('reputation');
sortByPopularityButton.addEventListener('click', () => {
        createPagination(sortedByPopularityUrl);
        init();
    }
);
let sortByNameButton = document.getElementById('new_users'); // Дурацкий ID конечно у кнопки
sortByNameButton.addEventListener('click', () => {
        createPagination(sortedByNameUrl);
        init();
    }
)
let sortByDateButton = document.getElementById('voters'); // Дурацкий ID конечно у кнопки
sortByDateButton.addEventListener('click', () => {
        createPagination(sortedByDateUrl);
        init();
    }
)
