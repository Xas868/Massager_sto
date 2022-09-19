const paginationByReputation = 'http://localhost:8091/api/user/reputation';
const paginationByVotes = 'http://localhost:8091/api/user/vote';
const paginationByNewcomers = 'http://localhost:8091/api/user/new';


activateSideBar()


function activateSideBar() {
    document.querySelector("#sidebar_users").classList.add("active")
}
let pagination;
//создаем новый объект пагинации и передаем аргументы в конструктор
function createPagination(url){
    pagination = new Pagination(
        url,                                                          //url
        16,                                                     //количество объектов
        'users_grid',                                    //id div куда будут вставляться объекты
        'users_navigation',                                //id div куду будет вставляться нумерация
        function (arrayObjects) {                             //функция, которая задаёт - как будут вставляться объекты

            let mainDiv = document.createElement('div');

            if (arrayObjects != null && arrayObjects.length > 0) {

                let numCardsInDeck = 0;
                let cardDeckDiv = undefined;

                for (let num = 0; num < arrayObjects.length; num++) {

                    if (numCardsInDeck === 0) {
                        numCardsInDeck = 4;
                        cardDeckDiv = document.createElement('div');
                        cardDeckDiv.className = "card-deck";
                        mainDiv.appendChild(cardDeckDiv);
                    }

                    let cardDiv = document.createElement('div');
                    cardDiv.className = "card border-0";
                    cardDiv.innerHTML =
                        `
            <div class="user-card">
                <div><img src=${arrayObjects[num].imageLink} class="avatar card-img-top" alt="..."></div>
                <div class="card-body">
                    <div class="card-title"><a href="#">${arrayObjects[num].fullName}</a></div>
                    <div class="card-text small">${arrayObjects[num].city}</div>
                    <div class="card-text font-weight-bolder small">${arrayObjects[num].reputation}</div>
                    <div class="card-text small"></div>
                </div>
            </div>
        `;
                    let output = '';
                    arrayObjects[num].listTagDto.forEach(tag => {
                        output += '<a href="#">' + tag.name + '</a>, ';
                    });
                    if (output !== '') {
                        output = output.slice(0, -2);
                    }
                    cardDiv.children[0].children[1].children[3].innerHTML = output;
                    cardDeckDiv.appendChild(cardDiv);
                    numCardsInDeck--;
                }
            }
            return mainDiv;
        });
}

createPagination(paginationByReputation);
init();

function showPage(event, num) {
    pagination.showPage(event, num);
}

async function init() {
    await pagination.showPage(null, 1);
}

document.getElementById("filterByUser").oninput = async function() {
    pagination.filter=document.getElementById("filterByUser").value;
    await init();
};

let sortByReputationButton = document.getElementById('reputation');
sortByReputationButton.addEventListener('click', () => {
        createPagination(paginationByReputation);
        init();
    }
);
let sortByNameButton = document.getElementById('new_users');
sortByNameButton.addEventListener('click', () => {
        createPagination(paginationByNewcomers);
        init();
    }
)
let sortByVotesButton = document.getElementById('voters');
sortByVotesButton.addEventListener('click', () => {
        createPagination(paginationByVotes);
        init();
    }
)