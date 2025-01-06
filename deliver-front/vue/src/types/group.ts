export interface GroupCard {
	groupId: number;
	groupName: string;
	groupDescription: string;
	updateTime?: string;
	topUp?: number;
}
export interface GroupCardList {
	topUpGroupList: GroupCard[];
	defaultGroupList: GroupCard[];
}
