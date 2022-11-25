import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An Election consists of the candidates running for office, the ballots that 
 * have been cast, and the total number of voters.  This class implements the 
 * ranked choice voting algorithm.
 * 
 * Ranked choice voting uses this process:
 * <ol>
 * <li>Rather than vote for a single candidate, a voter ranks all the 
 * candidates.  For example, if 3 candidates are running on the ballot, a voter
 * identifies their first choice, second choice, and third choice.
 * <li>The first-choice votes are tallied.If any candidate receives &gt; 50% 
 * of the votes, that candidate wins.
 * <li>If no candidate wins &gt; 50% of the votes, the candidate(s) with the 
 * lowest number of votes is(are) eliminated.  For each ballot in which an
 * eliminated candidate is the first choice, the 2nd ranked candidate is now
 * the top choice for that ballot.
 * <li>Steps 2 &amp; 3 are repeated until a candidate wins, or all remaining 
 * candidates have exactly the same number of votes.  In the case of a tie, 
 * there would be a separate election involving just the tied candidates.
 * </ol>
 */
public class Election {
    // All candidates that were in the election initially.  If a candidate is 
    // eliminated, they will still stay in this array.
    private final Candidate[] candidates;
    
    // The next slot in the candidates array to fill.
    private int nextCandidate;
    
    /**
     * Create a new Election object.  Initially, there are no candidates or 
     * votes.
     * @param numCandidates the number of candidates in the election.
     */
    public Election (int numCandidates) {
        this.candidates = new Candidate[numCandidates];
    }
    
    /**
     * Adds a candidate to the election
     * @param name the candidate's name
     */
    public void addCandidate (String name) {
        candidates[nextCandidate] = new Candidate (name);
        nextCandidate++;
    }
    
    /**
     * Adds a completed ballot to the election.
     * @param ranks A correctly formulated ballot will have exactly 1 
     * entry with a rank of 1, exactly one entry with a rank of 2, etc.  If 
     * there are n candidates on the ballot, the values in the rank array 
     * passed to the constructor will be some permutation of the numbers 1 to 
     * n.
     * @throws IllegalArgumentException if the ballot is not valid.
     */
    public void addBallot (int[] ranks) {
        if (!isBallotValid(ranks)) {
            throw new IllegalArgumentException("Invalid ballot");
        }
        Ballot newBallot = new Ballot(ranks);
        assignBallotToCandidate(newBallot);
    }

    /**
     * Checks that the ballot is the right length and contains a permutation 
     * of the numbers 1 to n, where n is the number of candidates.
     * @param ranks the ballot to check
     * @return true if the ballot is valid.
     */
    private boolean isBallotValid(int[] ranks) {
        if (ranks.length != candidates.length) {
            return false;
        }
        int[] sortedRanks = Arrays.copyOf(ranks, ranks.length);
        Arrays.sort(sortedRanks);
        for (int i = 0; i < sortedRanks.length; i++) {
            if (sortedRanks[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines which candidate is the top choice on the ballot and gives the
     * ballot to that candidate.
     * @param newBallot a ballot that is not currently assigned to a candidate
     */
    private void assignBallotToCandidate(Ballot newBallot) {
        int candidate = newBallot.getTopCandidate();
        candidates[candidate].addBallot(newBallot);
    }
    
    /**
     * Finds the position of the candidate with lowest total votes
     * @param voteCount an arrayList with total first preference votes for each candidate
     * @return position of the candidate with the lowest top choice votes
     */
    private int smallestTotalCandidate(List<Integer> voteCount) {
    	int min = voteCount.get(0);
    	int position = 0;
    	for(int i = 1; i < voteCount.size(); i++) {
    		if(voteCount.get(i) < min) {
    			min = voteCount.get(i);
    			position = i;
    		}
    	}
    	return position;
    }
    
    /**
     * Finds the position of the candidate with the higher total votes
     * @param voteCount an arrayList with total first preference votes for each candidate
     * @return position of the candidate with the largest top choice votes
     */
    private int largestTotalCandidate(List<Integer> voteCount) {
    	int max = voteCount.get(0);
    	int position = 0;
    	for(int i = 1; i < voteCount.size(); i++) {
    		if(voteCount.get(i) > max) {
    			max = voteCount.get(i);
    			position = i;
    		}
    	}
    	return position;
    }
    
    /**
     * finds the largest number in the array list
     * @param voteCount an arrayList with total first preference votes for each candidate
     * @return largest number in the array list
     */
    private int largestTotal(List<Integer> voteCount) {
    	int max = voteCount.get(0);
    	for(int i = 1; i < voteCount.size(); i++) {
    		if(voteCount.get(i) > max) {
    			max = voteCount.get(i);
    		}
    	}
    	return max;
    }
    
    /**
     * creates an array list with total number of first preference votes for each candidate 
     * @param names array with names of all candidates
     * @return array list with total top choice votes for every candidate
     */
    private ArrayList<Integer> totalVotes(Candidate[] names){
    	ArrayList<Integer> topVoteCount = new ArrayList<>();
    	for(Candidate candidate: names) {
    		topVoteCount.add(candidate.getVotes());
    	}
    	return topVoteCount;
    }
    
    /**
     * Calculates the number of votes a candidate needs to win
     * @param voteCount an arrayList with total first preference votes for each candidate
     * @return number votes needed to win
     */
    private int votesToWin(List<Integer> voteCount) {
    	int total = 0;
    	int toWin = 0;
    	for(int i = 0; i < voteCount.size(); i++) {
    		total += voteCount.get(i);
    	}toWin = total / 2 + 1;
    	
    	return toWin;
    }

    /**
     * check if there are candidates with tied number of votes 
     * @param votes
     * @return boolean indicating whether the candidates are tied or not
     */
    private boolean checkIfTied(List<Integer> voteCount) {
    	boolean tied = false;
    	ArrayList<Integer> tiedCandidates = new ArrayList<>();
    	ArrayList<Integer> notTiedCandidates = new ArrayList<>();
    	int largestTotalVotes = largestTotal(voteCount);
    	
    	//add all the tied votes to the tiedVotes array list
    	for(int vote : voteCount) {
    		if(vote == voteCount.get(0)) {
    			tiedCandidates.add(vote);
    		} 
    	}
    	//if the tied votes array list is equal to the given array list
    	//all the candidates have equal number of top-choice votes
    	if(tiedCandidates.size() == voteCount.size()) {
    		tied = true;
    	//if the tied votes array list is not equal to the given array list 
    	}else{
    		for(int num : voteCount) {
    			//add tied votes to the tied votes array list
    	    	if(num == largestTotalVotes) {
    	    		tiedCandidates.add(num);
    	    	//add not tied votes to the not tied votes array list
    	    	} else {
    	    		notTiedCandidates.add(num);
    	   		}
    	   	}
    		//check if the votes have been allocated
    	   	for(int num : notTiedCandidates) {
    	   		if(num == 0) {
        			tied = true;
   	    		}
   	    	}
   		}
   	return tied;
    }
    	

    /**
     * gets the candidate at a specific position
     * @param position of the candidate needed
     * @return candidate at a given position
     */
    private Candidate getCandidate(int position) {
    	Candidate candidate = candidates[position];
    	return candidate;
    }
    
    /**
     * if there is no candidate with majority vote,
     * eliminates the candidate with the lowest amount of first preference votes
     * and adds a ballot to candidates that have second preference 
     * in the ballots an eliminated candidate is a top choice
     * 
     * @param voteCount an arrayList with total first preference votes for each candidate 
     */
    private void allocateVote(List<Integer> voteCount) {
    	int secondPreferencePosition = 0;
    	//position of candidate with least number of top choice votes
    	int eliminatePosition = smallestTotalCandidate(voteCount);
    	Candidate candidateName = getCandidate(eliminatePosition);
    	//eliminates a candidate
    	//and returns all of the ballots for which this candidate was the top choice
    	List<Ballot> toAllocate = candidateName.eliminate();
    	for(int i = 0; i < toAllocate.size(); i++) {
    		toAllocate.get(i).eliminateCandidate(eliminatePosition);
    		Ballot ballot = toAllocate.get(i);
    		secondPreferencePosition = ballot.getTopCandidate();
    		Candidate secondCandidate = getCandidate(secondPreferencePosition);
    		secondCandidate.addBallot(ballot);
    	}
    }
    
    /**
     * Apply the ranked choice voting algorithm to identify the winner.
     * 
     * @return If there is a winner, this method returns a list containing just
     * the winner's name is returned.  If there is a tie, this method returns a
     * list containing the names of the tied candidates.
     */
    public List<String> selectWinner () {
    	ArrayList<String> winner= new ArrayList<String>();
    	ArrayList<Integer> voteCounts = totalVotes(candidates);
    	String name;
    	Candidate candidate;
    	int mostVotePosition = largestTotalCandidate(voteCounts);
    	 
    	//if a candidate has a majority vote, that candidate is added to the winner array list
    	if( largestTotal(voteCounts)>= votesToWin(voteCounts)) {
			candidate = getCandidate(mostVotePosition);
			name = candidate.getName();
			winner.add(name);
			return winner;
			// if candidates are tied, add all of tied candidates to the winner list
    	} else if(checkIfTied(voteCounts)){
    		for(int i = 0; i < voteCounts.size(); i++) {
    			if(voteCounts.get(i) == largestTotal(voteCounts)) {
    				name = getCandidate(i).getName();
    				winner.add(name);
    			}
    		}
    		return winner;
    		//if no candidate has a majority vote, votes are allocated and selectWinner is recursively called 
		} else {
			allocateVote(voteCounts);
			return selectWinner();
		}
    	
    }
}