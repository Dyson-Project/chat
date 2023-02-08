enum ChatType {
    PERSONAL, GROUP
}

interface Chat {
    id: number;
    title: string;
    type: ChatType;
    data: string;
}